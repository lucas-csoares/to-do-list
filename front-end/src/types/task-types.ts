export interface Task {
  id: number;
  titulo: string;
  dataPrevisao: string;
  prazo: number;
  prioridade: string;
  dataInicio: string;
  dataAtualizacao: string;
  statusConformeTipo: string;
  dataFormatada: string;
}

export interface DialogTarefaProps {
    task: Task;
  }

export enum Prioridade {
  ALTA = "ALTA",
  MÉDIA = "MEDIA",
  BAIXA = "BAIXA"
}

export enum TaskStatus {
  EM_PROGRESSO = "EM_PROGRESSO",
  FINALIZADA = "FINALIZADA"
}

export interface UpdatedTaskPrazo  {
    titulo: string;
    prioridade: string;
    prazo: number | null;
    dataPrevisao?: never;
}

export interface UpdatedTaskData  {
    titulo: string;
    prioridade: string;
    dataPrevisao: string;
    prazo?: never;
}
