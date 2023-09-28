import ExpoGainsightPxModule from './ExpoGainsightPxModule';

export function hello(): string {
  return ExpoGainsightPxModule.hello();
}

export function startInstance(apiKey: string) {
  return ExpoGainsightPxModule.startInstance(apiKey);
}

export function identify(userId: string) {
  return ExpoGainsightPxModule.identify(userId);
}

export function custom(eventName: string) {
  return ExpoGainsightPxModule.custom(eventName);
}