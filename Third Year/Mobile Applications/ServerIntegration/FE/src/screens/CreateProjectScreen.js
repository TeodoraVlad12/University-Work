import React, { useState } from 'react';
import { View, StyleSheet } from 'react-native';
import { TextInput, Button, Text, TouchableRipple, HelperText, useTheme } from 'react-native-paper';
import DateTimePicker from '@react-native-community/datetimepicker';
import { useDispatch } from 'react-redux';
import { addProjectThunk } from '../redux/projectSlice';
import { Picker } from '@react-native-picker/picker';
import Toast from 'react-native-toast-message';

const CreateProjectScreen = ({ navigation }) => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [urgency, setUrgency] = useState('MEDIUM');
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(null);
    const [showStartDatePicker, setShowStartDatePicker] = useState(false);
    const [showEndDatePicker, setShowEndDatePicker] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const dispatch = useDispatch();
    const theme = useTheme();

    const handleCreateProject = async () => {
        if (!name || !description || !startDate) {
            setErrorMessage('Name, Description, and Start Date are required.');
            return;
        }

        setErrorMessage(''); // Clear any previous errors
        const newProject = {
            name,
            description,
            urgency,
            startDate: startDate.toISOString(),
            endDate: endDate ? endDate.toISOString() : null,
        };

        try {

            await dispatch(addProjectThunk(newProject));

            navigation.navigate('Main', { successMessage: 'Project created successfully!' });

        } catch (error) {

            console.error('Error creating project:', error);
            Toast.show({
                type: 'error',
                text1: 'Failed to create project',
                position: 'bottom',
                visibilityTime: 3000,
            });
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Create New Project</Text>

            <TextInput
                label="Project Name"
                value={name}
                onChangeText={setName}
                mode="outlined"
                style={styles.input}
            />

            <TextInput
                label="Description"
                value={description}
                onChangeText={setDescription}
                mode="outlined"
                multiline
                style={styles.input}
            />

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

            <TouchableRipple onPress={() => setShowStartDatePicker(true)} style={styles.datePicker}>
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

            <TouchableRipple onPress={() => setShowEndDatePicker(true)} style={styles.datePicker}>
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

            {/* Display Error Message */}
            {errorMessage && (
                <HelperText type="error" style={styles.errorText}>
                    {errorMessage}
                </HelperText>
            )}

            {/* Create Button */}
            <Button
                mode="contained"
                onPress={handleCreateProject}
                style={styles.createButton}
                buttonColor={theme.colors.primary}
            >
                Create Project
            </Button>
        </View>
    );
};


            /*const CreateProjectScreen = ({ navigation }) => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [urgency, setUrgency] = useState('MEDIUM');
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(null);
    const [showStartDatePicker, setShowStartDatePicker] = useState(false);
    const [showEndDatePicker, setShowEndDatePicker] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const dispatch = useDispatch();
    const theme = useTheme();



    const handleCreateProject = () => {
        if (!name || !description || !startDate) {
            setErrorMessage('Name, Description, and Start Date are required.');
            return;
        }

        const newProject = {
            id: Date.now(),
            name,
            description,
            urgency,
            startDate: startDate.toISOString(),
            endDate: endDate ? endDate.toISOString() : null,
        };

        dispatch(addProjectThunk(newProject));

        navigation.navigate('Main', { successMessage: 'Project Created successfully!' });

    };

    return (
        <View style={styles.container}>
            {/!* Title *!/}
            <Text style={styles.title}>Create New Project</Text>

            {/!* Project Name Input *!/}
            <TextInput
                label="Project Name"
                value={name}
                onChangeText={setName}
                mode="outlined"
                style={styles.input}
            />

            {/!* Project Description Input *!/}
            <TextInput
                label="Description"
                value={description}
                onChangeText={setDescription}
                mode="outlined"
                multiline
                style={styles.input}
            />

            {/!* Urgency Dropdown *!/}
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

            {/!* Start Date Picker *!/}
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

            {/!* End Date Picker *!/}
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

            {/!* Error Message *!/}
            {errorMessage && (
                <HelperText type="error" style={styles.errorText}>
                    {errorMessage}
                </HelperText>
            )}

            {/!* Create Button *!/}
            <Button
                mode="contained"
                onPress={handleCreateProject}
                style={styles.createButton}
                buttonColor={theme.colors.primary}
            >
                Create Project
            </Button>
        </View>
    );
};*/

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
    errorText: {
        marginBottom: 10,
    },
    createButton: {
        marginTop: 20,
    },
});

export default CreateProjectScreen;
