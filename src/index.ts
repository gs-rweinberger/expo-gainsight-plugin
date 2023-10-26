import { EventEmitter, Subscription } from 'expo-modules-core';
import ExpoGainsightPxModule from './ExpoGainsightPxModule';

export const enum Status {
  FAILURE = 0,
  SUCCESS = 1
}

export type Response = {
  status: Status;
  methodName?: string;
  params?: object;
  exceptionMessage?: string;
};

export enum PXHost {
  us = "us",
  eu = "eu",
  us2 = "us2"
}

export type Configuration = {
  apiKey: string,
  flushQueueSize?: number,
  maxQueueSize?: number,
  flushIntervalInSeconds?: number,
  enableLogs?: boolean,
  trackApplicationLifeCycleEvents?: boolean,
  shouldTrackTapEvents?: boolean,
  enable?: boolean,
  proxy?: string,
  host?: PXHost,
  reportTrackingIssues?: boolean,
  
  androidIsTrackTapInAllLayouts?: boolean,
  androidCollectDeviceId?: boolean
}

export interface JsonList extends Array<JsonValue> {}

export type GlobalContextValue = boolean | number | string;

export type JsonValue = boolean | number | string | null | JsonList;

export type SupportedDates = number | string | null | undefined;

export type JsonMap = {
  [key: string]: JsonValue
}

export enum Gender {
  NOT_SET = "Not Set", 
  MALE = "Male", 
  FEMALE = "Female",
  OTHER = "Other"
}

export type User = {
  id: string,
  email?: string,
  userHash?: string,
  gender?: Gender,
  lastName?: string,
  firstName?: string,
  signUpDate?: SupportedDates,
  title?: string,
  role?: string,
  subscriptionId?: string,
  phone?: string,
  organization?: string,
  organizationEmployees?: string,
  organizationRevenue?: string,
  organizationIndustry?: string,
  /**
   * Should be Integer
   */
  organizationSicCode?: number,
  /** 
   * should be long 
   */
  organizationDuns?: number,
  accountId?: string,
  firstVisitDate?: SupportedDates,
  score?: number,
  sfdcContactId?: string,
  countryCode?: string,
  countryName?: string,
  stateCode?: string,
  stateName?: string,
  city?: string,
  street?: string,
  continent?: string,
  postalCode?: string,
  regionName?: string,
  timeZone?: string,
  latitude?: number,
  longitude?: number,
  customAttributes?: JsonMap
}

export type Account = {
  id: string,
  name?: string,
  trackedSubscriptionId?: string,
  industry?: string,
  numberOfEmployees?: number,
  /**
   * Should be Integer
   */
  sicCode?: number,
  website?: string,
  naicsCode?: string,
  plan?: string,
  sfdcId?: string,
  countryCode?: string,
  countryName?: string,
  stateCode?: string,
  stateName?: string,
  city?: string,
  street?: string,
  continent?: string,
  postalCode?: string,
  regionName?: string,
  timeZone?: string,
  latitude?: number,
  longitude?: number,
  customAttributes?: JsonMap
}

const emitter = new EventEmitter(ExpoGainsightPxModule);

export type EngagementInfo = {
  engagementId: String;
  engagementName?: String;
  screenName?: String;
  screenClass?: String;
  actionText?: String;
  actionData?: String;
  actionType?: String;
  params?: JsonMap;
};

export type ExceptionInfo = {
  method: String;
  params?: JsonMap;
  message?: String;
};

export function startInstance(configuration: Configuration): Response {
  return ExpoGainsightPxModule.startInstance(configuration);
}

export function isEnabled(): boolean {
  return ExpoGainsightPxModule.enabled;
}

export function setEnabled(value: boolean) {
  ExpoGainsightPxModule.enabled = value;
}

export function isEngagementsEnable(): boolean {
  return ExpoGainsightPxModule.engagementEnable;
}

export function setEngagementsEnable(value: boolean) {
  ExpoGainsightPxModule.engagementEnable = value;
}

export function identifyWithUserName(userId: string): Response  {
  return identify({id: userId});
}

export function identify(user: User, account?: Account): Response  {
  return ExpoGainsightPxModule.identify(user, account);
}

export function unidentify(): Response {
  return ExpoGainsightPxModule.unidentify();
}

export function custom(eventName: string, properties?: JsonMap): Response {
  return ExpoGainsightPxModule.custom(eventName, properties);
}

export function screen(screenName: string, screenClass?: string, properties?: JsonMap): Response {
  return ExpoGainsightPxModule.screen(screenName, screenClass, properties);
}

export function addGlobalContext(key: string, value: GlobalContextValue): Response {
  return ExpoGainsightPxModule.addGlobalContext(key, value);
}

export function hasGlobalContextKey(key: string): Boolean {
  return ExpoGainsightPxModule.hasGlobalContextKey(key);
}

export function removeGlobalContext(key: string): Response {
  return ExpoGainsightPxModule.removeGlobalContext(key);
}

export function flush(): Response{
  return ExpoGainsightPxModule.flush();
}

export function hardReset(): Response {
  return ExpoGainsightPxModule.hardReset();
}

export function addEngagementsListener(
  listener: (event: EngagementInfo) => void
): Subscription {
  return emitter.addListener<EngagementInfo>("onEngagementEvent", listener);
}

export function addExceptionListener(
  listener: (exception: ExceptionInfo) => void
): Subscription {
  return emitter.addListener<ExceptionInfo>("onExceptionThrown", listener)
}

export function enterEditing(deepLink: string): Response {
  return ExpoGainsightPxModule.enterEditing(deepLink);
}

export function exitEditing(): Response {
  return ExpoGainsightPxModule.exitEditing();
}


/*
type CallBackFn = (...args: any[]) => Promise<Response>;

async function runAsync(runnable: CallBackFn): Promise<Response> {
  return new Promise<Response>((resolve, reject) => {
    try {
    runnable().then(result => {
      if (result.status == Status.SUCCESS) {
        resolve(result);
      } else {
        reject(result);
      }
    }).catch(error => {
      const result: Response = {
        status: Status.FAILURE,
        methodName: error.userInfo.methodName,
        params: error.userInfo.params,
        exceptionMessage: error.message
      };
      reject(result);
      });    
  } catch (error) {
    const result: Response = {
      status: Status.FAILURE,
      methodName: error.userInfo.methodName,
      params: error.userInfo.params,
      exceptionMessage: error.message
    };
    reject(result);
  }});
}

export async function startInstance(configuration: Configuration): Promise<Response> {
  return runAsync(() =>{
    return ExpoGainsightPxModule.startInstance(configuration);
  });
}
*/