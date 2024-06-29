import { createTask, createTaskData, createTaskPrazo } from "@/api/fetch-tasks";
import { format } from 'date-fns';

export const handleSubmitTarefa = async (
    e: React.FormEvent<HTMLFormElement>,
    titulo: string,
    prioridade: string
  ) => {
    e.preventDefault();
    try {
      await createTask(titulo, prioridade);
      alert("Tarefa criada com sucesso!");
    } catch (error) {
      console.error(error);
      alert("Erro ao criar tarefa");
    }
  };

  export const handleSubmitData = async (
    e: React.FormEvent<HTMLFormElement>,
    titulo: string,
    prioridade: string,
    dataPrevisao: string,
  ) => {
    const dataFormatada = format(new Date(dataPrevisao), 'dd/MM/yyyy');
    e.preventDefault();
    try {
      await createTaskData(titulo, prioridade, dataFormatada);
      alert("Tarefa criada com sucesso!");
    } catch (error) {
      console.error(error);
      alert("Erro ao criar tarefa");
    }
  };

  export const handleSubmitPrazo = async (
    e: React.FormEvent<HTMLFormElement>,
    titulo: string,
    prioridade: string,
    prazo: number | null
  ) => {
    e.preventDefault();
    try {
      await createTaskPrazo(titulo, prioridade, prazo);
      alert("Tarefa criada com sucesso!");
    } catch (error) {
      console.error(error);
      alert("Erro ao criar tarefa");
    }
  };