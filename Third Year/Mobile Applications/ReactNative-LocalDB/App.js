import React from 'react';
import { Provider } from 'react-redux';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import MainScreen from './src/screens/MainScreen';
import CreateProjectScreen from './src/screens/CreateProjectScreen';
import EditProjectScreen from './src/screens/EditProjectScreen';
import store from './src/redux/store';
import Toast from 'react-native-toast-message';

const Stack = createStackNavigator();

const App = () => {
    return (
        <Provider store={store}>
            <NavigationContainer>
                <Stack.Navigator initialRouteName="Main">
                    {/* MainScreen is the first screen */}
                    <Stack.Screen
                        name="Main"
                        component={MainScreen}
                        options={{ title: 'My Projects',
                                    headerLeft: () => null }}

                    />
                    {/* The screen for creating a project */}
                    <Stack.Screen
                        name="CreateProject"
                        component={CreateProjectScreen}
                        options={{ title: 'Create Project' }}
                    />
                    {/* The screen for editing a project */}
                    <Stack.Screen
                        name="EditProject"
                        component={EditProjectScreen}
                        options={{ title: 'Edit Project' }}
                    />
                </Stack.Navigator>

                <Toast />
            </NavigationContainer>
        </Provider>
    );
};

export default App;
