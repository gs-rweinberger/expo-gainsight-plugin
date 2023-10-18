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

export class Configuration {
  private apiKey: string;
  public flushQueueSize?: number;
  public maxQueueSize?: number;
  public flushIntervalInSeconds?: number;
  public enableLogs?: boolean;
  public trackApplicationLifeCycleEvents?: boolean;
  public shouldTrackTapEvents?: boolean;
  public enable?: boolean;
  public proxy?: string;
  public host?: PXHost;
  public reportTrackingIssues?: boolean;

  public androidIsTrackTapInAllLayouts?: boolean;
  public androidCollectDeviceId?: boolean;

  // TODO: need to add crypto and connectionSettings
  constructor(apiKey: string) {
    this.apiKey = apiKey;
  }

  public toJson() {
    const json = JSON.stringify(this);
    return JSON.parse(json);
  }
}

export interface JsonList extends Array<JsonValue> {}

export type JsonValue = boolean | number | string | null | JsonList | JsonMap;

export interface JsonMap {
  [key: string]: JsonValue;

  [index: number]: JsonValue;
}

export class Geodetails {
  public countryCode?: string;
  public countryName?: string;
  public stateCode?: string;
  public stateName?: string;
  public city?: string;
  public street?: string;
  public continent?: string;
  public postalCode?: string;
  public regionName?: string;
  public timeZone?: string;
  public latitude?: number;
  public longitude?: number;

  public toJson() {
    const jstring = JSON.stringify(this);
    return JSON.parse(jstring);
  }
}

export class User extends Geodetails {
  public email?: string;
  public usem?: string;
  public userHash?: string;
  public gender?: string;
  public lastName?: string;
  public firstName?: string;
  public signUpDate?: Date;
  public title?: string;
  public role?: string;
  public subscriptionId?: string;
  public phone?: string;
  public organization?: string;
  public organizationEmployees?: string;
  public organizationRevenue?: string;
  public organizationIndustry?: string;
  public organizationSicCode?: string;
  public organizationDuns?: number;
  public accountId?: string;
  public firstVisitDate?: Date;
  public score?: number;
  public sfdcContactId?: string;
  public customAttributes?: JsonMap;

  private ide: string;

  constructor(userId: string) {
    super();
    this.ide = userId;
  }

  public toJson() {
    const jstring = JSON.stringify(this);
    return JSON.parse(jstring);
  }
}

export class Account extends Geodetails {
  public id: string;
  public name?: string;
  public trackedSubscriptionId?: string;
  public industry?: string;
  public numberOfEmployees?: number;
  public sicCode?: string;
  public website?: string;
  public naicsCode?: string;
  public plan?: string;
  public sfdcId?: string;
  public customAttributes?: JsonMap;

  constructor(id: string) {
    super();
    this.id = id;
  }

  public toJson() {
    const jstring = JSON.stringify(this);
    return JSON.parse(jstring);
  }
}

export function startInstance(configuration: Configuration): Response {
  return ExpoGainsightPxModule.startInstance(configuration);
}

export function identifyWithUserName(userId: string): Response  {
  return identify(new User(userId));
}

export function identify(user: User, account?: Account): Response  {
  return ExpoGainsightPxModule.identify(user, account);
}

export function custom(eventName: string) {
  return ExpoGainsightPxModule.custom(eventName);
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