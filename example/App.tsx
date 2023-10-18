import { SafeAreaView, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native';
import { useState } from 'react';

import * as ExpoGainsightPx from 'expo-gainsight-px';

export default function App() {
  const [custom, setCustom] = useState('');
  const [error, setError] = useState('');
/*
  type AsyncBoolean = (...args: any[]) => Promise<ExpoGainsightPx.Response>;

  async function runWithErrorCheck(method: AsyncBoolean): Promise<Boolean> {
    return new Promise<Boolean>((res,rej) => {
      method()
      .then(result => {
        res(analyzeResponse(result));
      })
      .catch(result => {
        res(analyzeResponse(result));
      });
    });
  }

  const gainsightFlow = async () => {
    const result = await runWithErrorCheck(init);
    if (result){
      identifyUser();
    }
  };
  */
 
  function analyzeResponse(response: ExpoGainsightPx.Response) : Boolean {
    if (response.status == ExpoGainsightPx.Status.FAILURE && !(response.exceptionMessage == null)) {
      setError(response.exceptionMessage);
      return false;
    }
    return true;
  }

  const gainsightFlow = () => {
    if (initGainsight()){
      identifyUser();
    }
  };

  const initGainsight = () =>{
    const config = new ExpoGainsightPx.Configuration("AP-EFDW8RWAJTAL-3");
    return analyzeResponse(ExpoGainsightPx.startInstance(config));
  };

  const identifyUser = () => {
    return analyzeResponse(ExpoGainsightPx.identifyWithUserName("expo-user"));
  };

  const customEvent = () => {
    if (analyzeResponse(ExpoGainsightPx.custom(custom))){
      setCustom("");
    }
  }

  return (
    <SafeAreaView style={styles.container}>
      <TouchableOpacity
        style={styles.ctaButton}
        onPress={gainsightFlow}
        >
        <Text style={styles.ctaButtonText}>
          Start Gainsight
        </Text>
      </TouchableOpacity>
      <TextInput
        value={custom}
        onChangeText={(eventName) => setCustom(eventName)}
        placeholder={'Custom Event'}
        style={styles.input}
      />
      <TouchableOpacity
        style={styles.ctaButton}
        onPress={customEvent}
        >
        <Text style={styles.ctaButtonText}>
          Send Custom
        </Text>
      </TouchableOpacity>
      <Text style={styles.ctaButton}>
        Error: {error}
      </Text>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  ctaButton: {
    height: 60,
    borderRadius: 8,
    backgroundColor: "purple",
    justifyContent: "center",
    alignItems: "center",
    marginHorizontal: 25,
    marginBottom: 10,
  },
  ctaButtonText: {
    color: "white",
    fontSize: 18,
    fontWeight: "bold",
  },
  input: {
    width: 250,
    height: 44,
    padding: 10,
    marginTop: 20,
    marginBottom: 10,
    backgroundColor: '#e8e8e8'
  },
});
