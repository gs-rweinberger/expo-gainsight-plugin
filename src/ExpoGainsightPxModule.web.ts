import { EventEmitter } from 'expo-modules-core';

const emitter = new EventEmitter({} as any);

export default {
  hello() {
    return 'Not Implemented';
  },
  startInstance(apiKey: string){
    // Not Implemented
  },
  identify(userId: string) {
    // Not Implemented
  }
};
