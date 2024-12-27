import React, { useState } from 'react';
import { View, StyleSheet } from 'react-native';
import { TextInput, Button, Text, TouchableRipple, useTheme, HelperText } from 'react-native-paper';
import DateTimePicker from '@react-native-community/datetimepicker';
import { useDispatch } from 'react-redux';
import { updateProjectThunk } from '../redux/projectSlice';
import { Picker } from '@react-native-picker/picker';
import Toast from 'react-native-toast-message';

const EditProjectScreen = ({ route, navigation }) => {
    const { project } = route.params;

    const [name, setName] = useState(project.name);
    const [description, setDescription] = useState(project.description);
    const [urgency, setUrgency] = useState(project.urgency);
    const [startDate, setStartDate] = useState(new Date(project.startDate));
    const [endDate, setEndDate] = useState(project.endDate ? new Date(project.endDate) : null);

    const [showStartDatePicker, setShowStartDatePicker] = useState(false);
    const [showEndDatePicker, setShowEndDatePicker] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const dispatch = useDispatch();
    const theme = useTheme();

    const handleSaveChanges = async () => {
        if (!name || !description || !startDate) {
            setErrorMessage('Name, Description, and Start Date are required.');
            return;
        }

        setErrorMessage('');

        const updatedProject = {
            ...project,
            name,
            description,
            urgency,
            startDate: startDate.toISOString(),
            endDate: endDate ? endDate.toISOString() : null,
        };

        try {
            await dispatch(updateProjectThunk(updatedProject));

            navigation.navigate('Main', { successMessage: 'Project updated successfully!' });
        } catch (error) {
            console.error('Error updating project:', error);

            Toast.show({
                type: 'error',
                text1: 'Failed to update project',
                position: 'bottom',
                visibilityTime: 3000,
            });
        }
    };

    return (
        <View style={styles.container}>
            {/* Title */}
            <Text style={styles.title}>Edit Project</Text>

            {/* Name Input */}
            <TextInput
                label="Project Name"
                value={name}
                onChangeText={setName}
                mode="outlined"
                style={styles.input}
            />

            {/* Description Input */}
            <TextInput
                label="Description"
                value={description}
                onChangeText={setDescription}
                mode="outlined"
                multiline
                style={styles.input}
            />

            {/* Urgency Dropdown */}
            <HelperText type="info" style={styles.helperText}>
                Urgency Level
            </HelperText>
            <View style={styles.pickerContainer}>
                <Picker
                    selectedValue={urgency}
                    onValueChange={(itemValue) => setUrgency(itemValue)}
                    style={styles.picker}
                >
                    <Picker.Item label="High" value="HIGH" />
                    <Picker.Item label="Medium" value="MEDIUM" />
                    <Picker.Item label="Low" value="LOW" />
                </Picker>
            </View>

            {/* Start Date Picker */}
            <TouchableRipple
                onPress={() => setShowStartDatePicker(true)}
                style={styles.datePicker}
            >
                <Text>
                    Start Date: {startDate ? startDate.toDateString() : 'Select a Start Date'}
                </Text>
            </TouchableRipple>
            {showStartDatePicker && (
                <DateTimePicker
                    value={startDate || new Date()}
                    mode="date"
                    display="default"
                    onChange={(event, selectedDate) => {
                        setShowStartDatePicker(false);
                        if (selectedDate) setStartDate(selectedDate);
                    }}
                />
            )}

            {/* End Date Picker */}
            <TouchableRipple
                onPress={() => setShowEndDatePicker(true)}
                style={styles.datePicker}
            >
                <Text>
                    End Date: {endDate ? endDate.toDateString() : 'Select an End Date (Optional)'}
                </Text>
            </TouchableRipple>
            {showEndDatePicker && (
                <DateTimePicker
                    value={endDate || new Date()}
                    mode="date"
                    display="default"
                    onChange={(event, selectedDate) => {
                        setShowEndDatePicker(false);
                        if (selectedDate) setEndDate(selectedDate);
                    }}
                />
            )}

            {/* Save Button */}
            <Button
                mode="contained"
                onPress={handleSaveChanges}
                style={styles.saveButton}
                buttonColor={theme.colors.primary}
            >
                Save Changes
            </Button>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        backgroundColor: '#f9f9f9',
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
        color: '#333',
    },
    input: {
        marginBottom: 15,
    },
    helperText: {
        fontSize: 14,
        marginBottom: 5,
    },
    pickerContainer: {
        borderWidth: 1,
        borderColor: '#ccc',
        borderRadius: 5,
        marginBottom: 15,
        overflow: 'hidden',
    },
    picker: {
        height: 50,
    },
    datePicker: {
        padding: 10,
        backgroundColor: '#e8e8e8',
        borderRadius: 5,
        marginBottom: 15,
    },
    saveButton: {
        marginTop: 20,
    },
});

export default EditProjectScreen;
