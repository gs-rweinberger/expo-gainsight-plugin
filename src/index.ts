import ExpoGainsightPxModule from './ExpoGainsightPxModule';

export const enum CallbackStatus {
  FAILURE = 0,
  SUCCESS = 1
}

export type Callback = {
  status: CallbackStatus;
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

export function startInstance(configuration: Configuration): Callback {
  return ExpoGainsightPxModule.startInstance(configuration);
}

export function identify(userId: string) {
  return ExpoGainsightPxModule.identify(userId);
}

export function custom(eventName: string) {
  return ExpoGainsightPxModule.custom(eventName);
}