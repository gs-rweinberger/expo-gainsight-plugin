import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

// Import the native module. On web, it will be resolved to ExpoGainsightPx.web.ts
// and on native platforms to ExpoGainsightPx.ts
import ExpoGainsightPxModule from './ExpoGainsightPxModule';
import ExpoGainsightPxView from './ExpoGainsightPxView';
import { ChangeEventPayload, ExpoGainsightPxViewProps } from './ExpoGainsightPx.types';

// Get the native constant value.
export const PI = ExpoGainsightPxModule.PI;

export function hello(): string {
  return ExpoGainsightPxModule.hello();
}

export async function setValueAsync(value: string) {
  return await ExpoGainsightPxModule.setValueAsync(value);
}

const emitter = new EventEmitter(ExpoGainsightPxModule ?? NativeModulesProxy.ExpoGainsightPx);

export function addChangeListener(listener: (event: ChangeEventPayload) => void): Subscription {
  return emitter.addListener<ChangeEventPayload>('onChange', listener);
}

export { ExpoGainsightPxView, ExpoGainsightPxViewProps, ChangeEventPayload };
