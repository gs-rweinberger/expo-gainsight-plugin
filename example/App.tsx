import { SafeAreaView, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native';
import { useEffect, useState } from 'react';

import * as ExpoGainsightPx from 'expo-gainsight-px';


export default function App() {
  const [custom, setCustom] = useState('');
  const [deeplink, setDeepLink] = useState('');
  const [error, setError] = useState('');
  const [exception, setException] = useState('');


  useEffect(() => {
    const subExcetion = ExpoGainsightPx.addExceptionListener((info) => {
      setException(`${info.method} - exception: ${info.message}\nargs: ${JSON.stringify(info.params)}`);
    });

    const subEngagement = ExpoGainsightPx.addEngagementsListener((info) => {
      setException(`Engagement Info:\n${JSON.stringify(info)}`);
    });

    return () => {
      subExcetion.remove();
      subEngagement.remove();
    }
  }, []);

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
      setError(response.methodName + ": " +response.exceptionMessage);
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
    const config: ExpoGainsightPx.Configuration = {apiKey: "AP-EFDW8RWAJTAL-3"};
    config.enableLogs = true;
    config.shouldTrackTapEvents = true;
    return analyzeResponse(ExpoGainsightPx.startInstance(config));
  };

  const identifyUser = () => {
    const user: ExpoGainsightPx.User = {id: "expo_reuven"};
    user.userHash = "abc";
    user.email = "reuven@expo.try";
    user.lastName = "Weinberger";
    user.firstName = "Reuven";
    user.title = "Mr.";
    user.role = "Developer";
    user.subscriptionId = "subId";
    user.phone = "+972505050505";
    user.organization = "Gainsight";
    user.organizationEmployees = "2000";
    user.organizationRevenue = "2M";
    user.organizationIndustry = "Software";
    user.score = 123.45;
    user.customAttributes = {
      hello: "work",
      bool: true,
      int:123,
      doub:123.42
    };
    user.signUpDate = new Date().toISOString();
    user.gender = ExpoGainsightPx.Gender.MALE;
    user.firstVisitDate = new Date().getTime();
    user.countryCode = "IL";
    user.countryName = "USA";
    user.stateCode = "51";
    user.stateName = "Test";
    user.city = "Reho";
    user.street = "There";
    user.continent = "Europe";
    user.postalCode = "1234567";
    user.regionName = "ME";
    user.timeZone = "EST";
    user.latitude = 34.2135;
    user.longitude = 36.123;
    user.sfdcContactId = "sfdc";
    user.organizationSicCode = 123.245;
    user.organizationDuns = 12312545.34;

    const account: ExpoGainsightPx.Account= {id: "def1"};
    account.countryCode = "IT";
    account.countryName = "Temp";
    account.stateCode = "IL";
    account.stateName = "Illinoy";
    account.city = "TestCity";
    account.street = "Test Street";
    account.continent = "Cont";
    account.postalCode = "9876543";
    account.regionName = "EM";
    account.timeZone = "GMT";
    account.latitude = 34.1234;
    account.longitude = 36.4321;

    account.name = "Gainsight TLD";
    account.trackedSubscriptionId = "1a2b3c4d";
    account.industry = "Analytics";
    account.numberOfEmployees = 1600;
    account.sicCode = 234.123;
    account.website = "http://Gainsight.com/";
    account.naicsCode = "1a2c3b4e";
    account.plan = "Gold";
    account.sfdcId = "12345";
    account.customAttributes = {
      hello: "work",
      acc_attr_boolean: true,
      acc_attr_number:123.42
    };

    return analyzeResponse(ExpoGainsightPx.identify(user, account));
  };

  const customEvent = () => {
    let properties = {
      att_str: "name",
      att_int: 123456,
      att_double: 456.123,
      att_bool: true
    };
    if (analyzeResponse(ExpoGainsightPx.custom(custom, properties))){
      setCustom("");
    }
  }

  const openEditor = () => {
    if (analyzeResponse(ExpoGainsightPx.enterEditing(deeplink))){
      setDeepLink("");
    }
  }

  const closeEditor = () => {
    analyzeResponse(ExpoGainsightPx.exitEditing());
  }

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.exception}>
        {exception}
      </Text>
      <Text style={styles.error}>
        {error}
      </Text>
      <View style={styles.spacer}/>
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
      <View style={styles.spacer}/>
      <TextInput
        value={deeplink}
        onChangeText={(link) => setDeepLink(link)}
        placeholder={'Editor URL'}
        style={styles.input}
      />
      <TouchableOpacity
        style={styles.ctaButton}
        onPress={openEditor}
        >
        <Text style={styles.ctaButtonText}>
          Open Editor
        </Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.ctaButton}
        onPress={closeEditor}
        >
        <Text style={styles.ctaButtonText}>
          Close Editor
        </Text>
      </TouchableOpacity>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'stretch',
    justifyContent: 'flex-start',
    marginVertical: 16,
    marginHorizontal: 25,
  },
  ctaButton: {
    borderRadius: 8,
    backgroundColor: "purple",
    justifyContent: "center",
    alignItems: "center",
    padding: 10,
    marginBottom: 10,
  },
  error: {
    borderRadius: 8,
    backgroundColor: "#efe8e0",
    color: '#0f0f0f',
    justifyContent: "center",
    alignItems: "center",
    marginBottom: 10,
    padding:10,
  },
  exception: {
    borderRadius: 8,
    backgroundColor: "#f0f0f0",
    color: '#0f0f0f',
    justifyContent: "center",
    alignItems: "center",
    padding:10,
    marginBottom: 10,
  },
  ctaButtonText: {
    color: "white",
    fontSize: 18,
    fontWeight: "bold",
  },
  input: {
    height: 44,
    padding: 10,
    borderRadius: 8,
    marginBottom: 10,    
    backgroundColor: '#e8e8e8'
  },
  spacer: {
    height: 16,
  },
});
