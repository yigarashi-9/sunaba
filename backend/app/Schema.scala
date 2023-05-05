package sunaba

import io.circe.generic.auto._
import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.ISODateTimeFormat
import sangria.ast.StringValue
import sangria.macros.derive._
import sangria.relay._
import sangria.relay.Connection.Args._
import sangria.schema._
import sangria.marshalling.circe._
import sangria.marshalling.DateSupport
import sangria.validation.ValueCoercionViolation
import scala.util.{Try, Success, Failure}

trait MainService
    extends service.PartService
    with service.EventService
    with service.TrainingService
object MainService extends MainService

class GraphQLContext {
  val srv = MainService
}

object GraphQLSchema {
  case object DateCoercionViolation
      extends ValueCoercionViolation("Date value expected")
  def parseDate(s: String) = Try(new DateTime(s, DateTimeZone.UTC)) match {
    case Success(date) => Right(date)
    case Failure(_)    => Left(DateCoercionViolation)
  }
  implicit val DateTimeType = ScalarType[DateTime](
    "DateTime",
    coerceOutput = (d, caps) =>
      if (caps.contains(DateSupport)) d.toDate
      else ISODateTimeFormat.dateTime().print(d),
    coerceUserInput = {
      case s: String => parseDate(s)
      case _         => Left(DateCoercionViolation)
    },
    coerceInput = {
      case StringValue(s, _, _, _, _) => parseDate(s)
      case _                          => Left(DateCoercionViolation)
    }
  )

  val NodeDefinition(nodeInterface, nodeField, nodesField) =
    Node.definition(
      (globalId: GlobalId, ctx: Context[GraphQLContext, Unit]) =>
        globalId.typeName match {
          case "Part"     => ctx.ctx.srv.findPartById(globalId.id)
          case "Event"    => ctx.ctx.srv.findEventById(globalId.id)
          case "Training" => ctx.ctx.srv.findTrainingById(globalId.id)
          case _          => None
        },
      Node.possibleNodeTypes[GraphQLContext, Node](
        PartType,
        EventType,
        TrainingType
      )
    )

  implicit val PartType: ObjectType[GraphQLContext, model.Part] =
    deriveObjectType[GraphQLContext, model.Part](
      Interfaces[GraphQLContext, model.Part](nodeInterface)
    )

  implicit val EventType: ObjectType[GraphQLContext, model.Event] =
    deriveObjectType[GraphQLContext, model.Event](
      Interfaces[GraphQLContext, model.Event](nodeInterface),
      ExcludeFields("partID"),
      AddFields(
        Node.globalIdField,
        Field(
          "part",
          PartType,
          resolve = c =>
            c.ctx.srv
              .findPartById(c.value.partID)
              .fold(throw new Exception("Part does not exist."))(identity(_))
        )
      )
    )

  implicit val TrainingType: ObjectType[GraphQLContext, model.Training] =
    deriveObjectType[GraphQLContext, model.Training](
      Interfaces[GraphQLContext, model.Training](nodeInterface),
      ExcludeFields("eventID"),
      AddFields(
        Node.globalIdField,
        Field(
          "event",
          EventType,
          resolve = c =>
            c.ctx.srv
              .findEventById(c.value.eventID)
              .fold(throw new Exception("Part does not exist."))(identity(_))
        )
      )
    )

  val Id = Argument("id", IDType)
  val Since = Argument("since", DateTimeType)
  val QueryType = ObjectType(
    "Query",
    fields[GraphQLContext, Unit](
      nodeField,
      Field(
        "lastTrainings",
        ListType(TrainingType),
        arguments = Since :: Nil,
        resolve = c => c.ctx.srv.findLastTrainings(c.arg(Since))
      )
    )
  )

  case class EditTrainingInput(eventID: String, count: Int, weight: Option[Double])
  val EditTrainingInputType = deriveInputObjectType[EditTrainingInput]()
  val AddTrainingArg = Argument("input", EditTrainingInputType)

  val MutationType = ObjectType(
    "Mutation",
    fields[GraphQLContext, Unit](
      Field(
        "addTraining",
        TrainingType,
        arguments = AddTrainingArg :: Nil,
        resolve = c => {
          val input = c.arg(AddTrainingArg)
          c.ctx.srv.addTraining(input.eventID, input.count, input.weight)
        }
      ),
    )
  )

  val schema = Schema[GraphQLContext, Unit](QueryType, Some(MutationType))
}
