import React from 'react';
import { View, Text, FlatList, StyleSheet, TouchableOpacity } from 'react-native';

export const ProjectList = ({ projects, onEdit, onDelete }) => {
    const renderItem = ({ item }) => {
        const startDate = new Date(item.startDate);
        const endDate = item.endDate ? new Date(item.endDate) : null;

        return (
            <TouchableOpacity
                style={styles.card}
                onPress={() => onEdit(item)} // Clicking a card navigates to edit
            >
                <View style={styles.content}>
                    <Text style={styles.title}>{item.name}</Text>
                    <Text style={styles.date}>
                        {startDate.toDateString()}
                        {endDate && ` - ${endDate.toDateString()}`}
                    </Text>
                    <Text style={styles.description}>{item.description}</Text>
                    <Text style={styles.urgency}>Urgency: {item.urgency}</Text>
                </View>
                <TouchableOpacity
                    style={styles.smallDeleteButton}
                    onPress={() => onDelete(item.id)}
                >
                    <Text style={styles.deleteButtonText}>X</Text>
                </TouchableOpacity>
            </TouchableOpacity>
        );
    };

    return (
        <FlatList
            data={projects}
            renderItem={renderItem}
            keyExtractor={(item) => item.id.toString()}
            contentContainerStyle={styles.list}
        />
    );
};

const styles = StyleSheet.create({
    list: {
        paddingHorizontal: 16,
        paddingVertical: 8,
    },
    card: {
        backgroundColor: '#f3e5f5', // Light purple background
        borderRadius: 12, // Rounded corners
        marginBottom: 12,
        padding: 16,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 4,
        elevation: 3, // Shadow for Android
        position: 'relative',
    },
    content: {
        marginBottom: 8,
    },
    title: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#4F27A9', // Primary Dark Color
    },
    date: {
        fontSize: 12,
        color: '#974CE3', // Secondary Color
        marginBottom: 8, // Spacing between date and description
    },
    description: {
        fontSize: 14,
        color: '#6B3EA9', // Primary Color
        marginBottom: 8,
    },
    urgency: {
        fontSize: 12,
        color: '#BB86FC', // Light version of primary color
        fontStyle: 'italic',
    },
    smallDeleteButton: {
        backgroundColor: '#b25d6e', // Red background for delete button
        paddingVertical: 4,
        paddingHorizontal: 8,
        borderRadius: 6,
        position: 'absolute',
        top: 8,
        right: 8, // Positioned at the top-right corner of the card
    },
    deleteButtonText: {
        color: '#fff', // White text
        fontSize: 12,
        fontWeight: 'bold',
    },
});
