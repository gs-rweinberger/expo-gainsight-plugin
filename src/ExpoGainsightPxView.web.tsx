import * as React from 'react';

import { ExpoGainsightPxViewProps } from './ExpoGainsightPx.types';

export default function ExpoGainsightPxView(props: ExpoGainsightPxViewProps) {
  return (
    <div>
      <span>{props.name}</span>
    </div>
  );
}
