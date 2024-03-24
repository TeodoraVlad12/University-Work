import React, { useEffect, useRef, useState } from "react";
import { Dog } from "../model/Dog";
import Chart from "chart.js/auto";

interface Props {
    dogs: Dog[];
}

function BarChart({ dogs }: Props) {
    const [dogList, setDogList] = useState<Dog[]>(dogs);
    const chartRef = useRef<Chart<"bar"> | null>(null);

    useEffect(() => {
        setDogList(dogs);
    }, [dogs]);

    useEffect(() => {
        const canvas = document.getElementById("barChart") as HTMLCanvasElement | null;
        if (!canvas) {
            return; // Return early if canvas is not found
        }

        if (chartRef.current) {
            chartRef.current.destroy(); // Destroy previous chart instance
        }

        const ctx = canvas.getContext("2d");
        if (!ctx) {
            return; // Return early if canvas context is not available
        }

        const ageGroups = [0, 0, 0];
        dogList.forEach((dog) => {
            const age = dog.getAge();
            if (age < 5) {
                ageGroups[0]++;
            } else if (age >= 5 && age <= 10) {
                ageGroups[1]++;
            } else {
                ageGroups[2]++;
            }
        });

        const newChartInstance = new Chart(ctx, {
            type: "bar",
            data: {
                labels: ["<5", "[5,10]", ">10"],
                datasets: [
                    {
                        label: "Age",
                        data: ageGroups,
                        backgroundColor: [
                            "rgba(173, 216, 230, 0.6)",
                            "rgba(144, 238, 144, 0.6)",
                            "rgba(255, 192, 203, 0.6)",
                        ],
                    },
                ],
            },
        });

        chartRef.current = newChartInstance;

        // Clean up function
        return () => {
            if (chartRef.current) {
                chartRef.current.destroy(); // Ensure previous chart instance is destroyed on component unmount
            }
        };
    }, [dogList]);

    return <canvas id="barChart" />;
}

export default BarChart;
