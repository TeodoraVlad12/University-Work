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
    //useSelector() will also subscribe to the Redux store, and run your selector whenever an action is dispatched (so it will work with sockets ok)

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
