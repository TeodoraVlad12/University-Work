import React, { useEffect } from 'react';
import { View, Alert, StyleSheet, TouchableOpacity, Text } from 'react-native';
import { useSelector, useDispatch } from 'react-redux';
import { deleteProject } from '../redux/projectSlice';
import { ProjectList } from '../components/ProjectList';
import Toast from 'react-native-toast-message';
import {useTheme} from "react-native-paper";

const MainScreen = ({ route, navigation }) => {
    const projects = useSelector((state) => state.projects.projects); // Access state
    const dispatch = useDispatch(); // Dispatch actions
    const theme = useTheme();

    useEffect(() => {
        if (route.params?.successMessage) {
            Toast.show({
                type: 'success',
                position: 'bottom',
                text1: route.params.successMessage,
                visibilityTime: 4000,
                autoHide: true,
                style: {
                    backgroundColor: '#d579eb',
                }
            });
        }
    }, [route.params?.successMessage]);

    const handleDeleteProject = (projectId) => {
        // Show a confirmation dialog before deleting
        Alert.alert(
            'Delete Project',
            'Are you sure you want to delete this project?',
            [
                {
                    text: 'Cancel',
                    onPress: () => console.log('Delete Cancelled'),
                    style: 'cancel',
                },
                {
                    text: 'Delete',
                    onPress: () => dispatch(deleteProject(projectId)), // Dispatch delete action
                    style: 'destructive',
                },
            ],
            { cancelable: true } // Can be canceled by tapping outside the dialog
        );
    };

    const handleEditProject = (project) => {
        navigation.navigate('EditProject', { project });
    };

    return (
        <View style={styles.container}>
            <ProjectList
                projects={projects}
                onEdit={handleEditProject}
                onDelete={handleDeleteProject}
            />

            {/* Floating Add Button */}
            <TouchableOpacity
                style={styles.fab}
                onPress={() => navigation.navigate('CreateProject')}
            >
                <Text style={styles.fabText}>+</Text>
            </TouchableOpacity>
        </View>
    );
};

export default MainScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
    },
    fab: {
        position: 'absolute',
        bottom: 16,
        right: 16,
        width: 56,
        height: 56,
        borderRadius: 28,
        backgroundColor: '#6B3EA9', // Primary color for the button
        justifyContent: 'center',
        alignItems: 'center',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 4,
        elevation: 5, // Shadow for Android
    },
    fabText: {
        fontSize: 24,
        color: '#fff',
        fontWeight: 'bold',
    },
});
