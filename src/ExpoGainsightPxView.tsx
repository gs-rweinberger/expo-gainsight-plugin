import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

import { ExpoGainsightPxViewProps } from './ExpoGainsightPx.types';

const NativeView: React.ComponentType<ExpoGainsightPxViewProps> =
  requireNativeViewManager('ExpoGainsightPx');

export default function ExpoGainsightPxView(props: ExpoGainsightPxViewProps) {
  return <NativeView {...props} />;
}
