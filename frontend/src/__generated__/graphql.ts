/* eslint-disable */
import { TypedDocumentNode as DocumentNode } from '@graphql-typed-document-node/core';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
  DateTime: any;
};

export type EditTrainingInput = {
  count: Scalars['Int'];
  eventID: Scalars['String'];
  weight?: InputMaybe<Scalars['Float']>;
};

export type Event = Node & {
  __typename?: 'Event';
  eventID: Scalars['String'];
  /** The ID of an object */
  id: Scalars['ID'];
  name: Scalars['String'];
  part: Part;
};

export type Mutation = {
  __typename?: 'Mutation';
  addTraining: Training;
};


export type MutationAddTrainingArgs = {
  input: EditTrainingInput;
};

/** An object with an ID */
export type Node = {
  /** The id of the object. */
  id: Scalars['ID'];
};

export type Part = Node & {
  __typename?: 'Part';
  /** The id of the object. */
  id: Scalars['ID'];
  name: Scalars['String'];
  partID: Scalars['String'];
};

export type Query = {
  __typename?: 'Query';
  lastTrainings: Array<Training>;
  /** Fetches an object given its ID */
  node?: Maybe<Node>;
};


export type QueryLastTrainingsArgs = {
  since: Scalars['DateTime'];
};


export type QueryNodeArgs = {
  id: Scalars['ID'];
};

export type Training = Node & {
  __typename?: 'Training';
  count: Scalars['Int'];
  createdAt: Scalars['DateTime'];
  event: Event;
  /** The ID of an object */
  id: Scalars['ID'];
  trainingID: Scalars['String'];
  weight?: Maybe<Scalars['Float']>;
};

export type GetTrainingsQueryVariables = Exact<{
  since: Scalars['DateTime'];
}>;


export type GetTrainingsQuery = { __typename?: 'Query', lastTrainings: Array<{ __typename?: 'Training', id: string, weight?: number | null, count: number, createdAt: any, event: { __typename?: 'Event', name: string, part: { __typename?: 'Part', name: string } } }> };


export const GetTrainingsDocument = {"kind":"Document","definitions":[{"kind":"OperationDefinition","operation":"query","name":{"kind":"Name","value":"GetTrainings"},"variableDefinitions":[{"kind":"VariableDefinition","variable":{"kind":"Variable","name":{"kind":"Name","value":"since"}},"type":{"kind":"NonNullType","type":{"kind":"NamedType","name":{"kind":"Name","value":"DateTime"}}}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"lastTrainings"},"arguments":[{"kind":"Argument","name":{"kind":"Name","value":"since"},"value":{"kind":"Variable","name":{"kind":"Name","value":"since"}}}],"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"event"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"name"}},{"kind":"Field","name":{"kind":"Name","value":"part"},"selectionSet":{"kind":"SelectionSet","selections":[{"kind":"Field","name":{"kind":"Name","value":"name"}}]}}]}},{"kind":"Field","name":{"kind":"Name","value":"id"}},{"kind":"Field","name":{"kind":"Name","value":"weight"}},{"kind":"Field","name":{"kind":"Name","value":"count"}},{"kind":"Field","name":{"kind":"Name","value":"createdAt"}}]}}]}}]} as unknown as DocumentNode<GetTrainingsQuery, GetTrainingsQueryVariables>;