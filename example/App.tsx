import { StyleSheet, Text, View } from 'react-native';

import * as ExpoGainsightPx from 'expo-gainsight-px';

export default function App() {
  return (
    <View style={styles.container}>
      <Text>{ExpoGainsightPx.hello()}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
