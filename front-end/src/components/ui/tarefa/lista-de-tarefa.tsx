"use client";
import { Task } from "@/types/task-types";
import React, { useEffect, useState } from "react";
import { DialogTarefa } from "./editar-tarefa";
import { deleteTask, fetchTasks, statusTask } from "@/api/fetch-tasks";
import { Button } from "../button";

const TaskList = () => {
  const [tasks, setTasks] = useState<Task[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchTasks();
        if (data && Array.isArray(data.tarefas)) {
          setTasks(data.tarefas);
        } else {
          console.error("Fetched data does not contain tasks array", data);
          setTasks([]);
        }
      } catch (error) {
        console.error("Error fetching tasks:", error);
        setTasks([]);
      }
    };

    fetchData();
  }, []);

  const handleDelete = async (taskId: number) => {
    try {
      await deleteTask(taskId);
      setTasks(tasks.filter(task => task.id !== taskId));
    } catch (error) {
      console.error("Error deleting task:", error);
    }
  };

  return (
    <div className="max-w-screen mx-auto bg-gray-800 p-8 rounded-lg mt-50">
      <h2 className="text-2xl mb-4 text-slate-300">Lista de Tarefas</h2>
      <ul className="max-h-80 overflow-y-auto grid gap-6">
        {tasks.map((task) => (
          <li
            key={task.id}
            className="mb-4 p-4 bg-gray-700 rounded-lg text-slate-300"
          >
            <h3 className="text-xl font-bold">{task.titulo}</h3>
            <div className="flex flex-1 space-x-2">
              <h4 className="font-bold">Previsão:</h4>
              <p>{task.dataPrevisao}</p>
            </div>
            <div className="flex flex-1 space-x-2">
              <h4 className="font-bold">Prazo:</h4>
              <p>{task.prazo}</p>
            </div>
            <div className="flex flex-1 space-x-2">
              <h4 className="font-bold">Prioridade:</h4>
              <p>{task.prioridade}</p>
            </div>
            <div className="flex flex-1 space-x-2">
              <h4 className="font-bold">Inicio:</h4>
              <p>{task.dataInicio}</p>
            </div>
            <div className="flex flex-1 space-x-2">
              <h4 className="font-bold">Atualização:</h4>
              <p>{task.dataAtualizacao}</p>
            </div>
            <div className="flex flex-1 space-x-2">
              <h4 className="font-bold">Status:</h4>
              <p>{task.statusConformeTipo}</p>
            </div>
            <div className="flex space-x-2 mt-4">
              <DialogTarefa task={task}/>
              <Button
                onClick={() => handleDelete(task.id)}
                size="sm"
                className="px-4 py-2 bg-red-500 hover:bg-red-600 rounded text-white"
              >
                Deletar
              </Button>
              <Button
                onClick={() => statusTask(task.id)}
                size="sm"
                className="px-4 bg-green-500 py-2 rounded text-white"
              >
                Concluir
              </Button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TaskList;
