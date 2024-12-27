import React, { useEffect } from 'react';
import { View, Alert, StyleSheet, TouchableOpacity, Text } from 'react-native';
import { useSelector, useDispatch } from 'react-redux';
import { deleteProjectThunk, getAllProjectsThunk } from '../redux/projectSlice';
import { ProjectList } from '../components/ProjectList';
import Toast from 'react-native-toast-message';
import {useTheme} from "react-native-paper";


const MainScreen = ({ route, navigation }) => {
    const dispatch = useDispatch();
    const theme = useTheme();

    // Retrieve the list of projects from the Redux store
    const projects = useSelector((state) => state.projects.projects);

    // Fetch projects from the database when the screen mounts
    useEffect(() => {
        if (projects.length === 0) {
            dispatch(getAllProjectsThunk());
        }
    }, [dispatch, projects.length]);

    // Show success toast when a successMessage is passed via navigation route
    useEffect(() => {
        if (route.params?.successMessage) {
            Toast.show({
                type: 'success',
                position: 'bottom',
                text1: route.params.successMessage,
                visibilityTime: 1500,
                autoHide: true,
                style: {
                    backgroundColor: theme.colors.primary,
                },
            });
        }
    }, [route.params?.successMessage, theme.colors.primary]);

    // Handle deleting a project with confirmation and toast feedback
    const handleDeleteProject = (projectId) => {
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
                    onPress: async () => {
                        try {
                            await dispatch(deleteProjectThunk(projectId)).unwrap(); // Use thunk to delete project
                            Toast.show({
                                type: 'success',
                                text1: 'Project deleted successfully',
                                position: 'bottom',
                                visibilityTime: 1500,
                            });
                        } catch (error) {
                            Toast.show({
                                type: 'error',
                                text1: 'Failed to delete project',
                                position: 'bottom',
                                visibilityTime: 1500,
                            });
                        }
                    },
                    style: 'destructive',
                },
            ],
            { cancelable: true }
        );
    };

    // Navigate to EditProject screen
    const handleEditProject = (project) => {
        navigation.navigate('EditProject', { project });
    };

    return (
        <View style={styles.container}>
            {/* Display an empty state if no projects exist */}
            {projects.length === 0 ? (
                <Text style={styles.emptyStateText}>No projects found. Tap "+" to add one!</Text>
            ) : (
                <ProjectList
                    projects={projects}
                    onEdit={handleEditProject}
                    onDelete={handleDeleteProject}
                />
            )}

            {/* Floating Add Button */}
            <TouchableOpacity
                style={[styles.fab, { backgroundColor: theme.colors.primary }]}
                onPress={() => navigation.navigate('CreateProject')}
            >
                <Text style={[styles.fabText, { color: theme.colors.onPrimary }]}>+</Text>
            </TouchableOpacity>
        </View>
    );
};

export default MainScreen;

/*const MainScreen = ({ route, navigation }) => {
        //route: Contains parameters passed from other screens, like successMessage.
        //navigation: Allows navigation between screen
    const projects = useSelector((state) => state.projects.projects); // we retrieve the list of projects from the redux store (state is the global state of my app)
    const dispatch = useDispatch(); // we use it to send actions to the redux store
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
        // Shows a confirmation dialog before deleting
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

            {/!* Floating Add Button *!/}
            <TouchableOpacity
                style={styles.fab}
                onPress={() => navigation.navigate('CreateProject')}
            >
                <Text style={styles.fabText}>+</Text>
            </TouchableOpacity>
        </View>
    );
};

export default MainScreen;*/

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
        elevation: 5,
    },
    fabText: {
        fontSize: 24,
        color: '#fff',
        fontWeight: 'bold',
    },
});
