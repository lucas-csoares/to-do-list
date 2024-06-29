import { Prioridade } from './../types/task-types';
const API_BASE_URL = "https://to-do-list-4zlv.onrender.com";

export const fetchTasks = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/tarefa`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to fetch tasks, status: ${response.status}`);
    }

    const data = await response.json();
    console.log("Resposta da API:", data);

    if (!data || data.length === 0) {
      console.error("Empty or malformed JSON data received:", data);
      return [];
    }

    return data;
  } catch (error) {
    console.error("Error fetching tasks:", error);
    return [];
  }
};

export const updateTask = async (taskId: number, updatedTask: any) => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/tarefa/${taskId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedTask),
    });

    if (!response.ok) {
        fetchTasks();
      throw new Error("Failed to update task");
    }
  } catch (error) {
    console.error("Error updating task:", error);
  }
};

export const deleteTask = async (taskId: number) => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/tarefa/${taskId}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      throw new Error("Failed to delete task");
    }
  } catch (error) {
    console.error("Error deleting task:", error);
  }
};

export const statusTask = async (taskId: number) => {
  try {
    const response = await fetch(
      `${API_BASE_URL}/api/tarefa/${taskId}/status`,
      {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        }
      },
    );

    if (!response.ok) {
      throw new Error("Failed to complete task");
    }
  } catch (error) {
    console.error("Error completing task:", error);
  }
};

export const createTask = async (titulo: string, prioridade: string) => {
  const response = await fetch(`${API_BASE_URL}/api/tarefa`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ titulo, prioridade }),
  });
  if (!response.ok) {
    throw new Error("Erro ao criar tarefa");
  }
  fetchTasks();
};

export const createTaskData = async (titulo: string, prioridade: string, dataPrevisao: string) => {
    const response = await fetch(`${API_BASE_URL}/api/tarefa`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ titulo, prioridade, dataPrevisao }),
    });
    if (!response.ok) {
      throw new Error("Erro ao criar tarefa");
    }
    fetchTasks();
  };

  export const createTaskPrazo = async (titulo: string, prioridade: string, prazo: number | null) => {
    const response = await fetch(`${API_BASE_URL}/api/tarefa`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ titulo, prioridade, prazo }),
    });
    if (!response.ok) {
      throw new Error("Erro ao criar tarefa");
    }
    fetchTasks();
  };