import dayjs from "dayjs";
import { gql } from "@/src/__generated__/gql";
import { GetTrainingsQuery } from "@/src/__generated__/graphql";
import { getClient } from "@/src/lib/client";

const GET_TRAININGS = gql(/* GraphQL */ `
  query GetTrainings($since: DateTime!) {
    lastTrainings(since: $since) {
      event {
        name
        part {
          name
        }
      }
      id
      weight
      count
      createdAt
    }
  }
`);

const historyPeriod = 15;
const dateFormat = "YYYY-MM-DD";
type GroupedTrainings = {
  [key: string]: GetTrainingsQuery["lastTrainings"];
};

export default async function Home() {
  const since = dayjs()
    .startOf("day")
    .subtract(historyPeriod, "day")
    .toISOString();
  const client = getClient();
  const { loading, error, data } = await client.query({
    query: GET_TRAININGS,
    variables: { since },
  });

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error : {error.message}</p>;

  const dateToTrainings: GroupedTrainings = [...Array(historyPeriod)]
    .map((_, i) => i)
    .reduce((acc: GroupedTrainings, cur) => {
      const dateStr = dayjs()
        .startOf("day")
        .subtract(cur, "day")
        .format(dateFormat);
      acc[dateStr] = [];
      return acc;
    }, {});
  
  for (const trn of data!.lastTrainings) {
    const dateStr = dayjs(trn.createdAt).startOf("day").format(dateFormat);
    if (dateStr in dateToTrainings) {
      dateToTrainings[dateStr].push(trn);
    } else {
      dateToTrainings[dateStr] = [trn];
    }
  }

  const dateSortedTrainings = Object.entries(dateToTrainings);
  dateSortedTrainings.sort((a, b) => a[0].localeCompare(b[0])).reverse();

  return (
    <main className="">
      {dateSortedTrainings.map(([dateStr, trns]) => (
        <>
          <h2>{dateStr}</h2>
          {trns.length === 0 ? (
            <p>なし</p>
          ) : (
            <ul>
              {trns.map((trn) => (
                <li key={trn.id}>{trn.event.part.name}</li>
              ))}
            </ul>
          )}
        </>
      ))}
    </main>
  );
}
