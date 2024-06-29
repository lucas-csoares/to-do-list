import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { updateTask } from "@/api/fetch-tasks";
import { DialogTarefaProps } from "@/types/task-types";
import { format, parse } from "date-fns";

export function FormEdicaoTarefaData({ task }: DialogTarefaProps) {

  const [titulo, setTitulo] = useState(task.titulo);
  const [dataPrevisao, setDataPrevisao] = useState(format(new Date(task.dataPrevisao), "yyyy-dd-MM"));
  const [prioridade, setPrioridade] = useState(task.prioridade)

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const dataFormatada = format(parse(dataPrevisao, "yyyy-MM-dd", new Date()), "dd/MM/yyyy");

    const updatedTask = {
      titulo,
      dataPrevisao: dataFormatada,
      prioridade,
    };

    try {
      await updateTask(task.id, updatedTask);
    } catch (error) {
      console.error("Erro ao atualizar tarefa:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="grid gap-4 py-4">
        <div className="grid grid-cols-4 items-center gap-4">
          <Label htmlFor="titulo" className="text-right">
            Título
          </Label>
          <Input
            id="titulo"
            value={titulo}
            onChange={(e) => setTitulo(e.target.value)}
            type="text"
            className="col-span-3 bg-gray-700"
          />
        </div>
        <div className="grid grid-cols-4 items-center gap-4">
          <Label htmlFor="dataPrevisao" className="text-right">
            Data Previsão
          </Label>
          <Input
            id="dataPrevisao"
            type="date"
            value={dataPrevisao}
            onChange={(e) => setDataPrevisao(e.target.value)}
            className="col-span-3 bg-gray-700"
          />
        </div>
        <div className="grid grid-cols-4 items-center gap-4">
          <Label htmlFor="prioridade" className="text-right">
            Prioridade
          </Label>
          <select
            id="prioridade"
            name="prioridade"
            value={prioridade}
            onChange={(e) => setPrioridade(e.target.value)}
            className="w-full p-2 mb-4 rounded bg-gray-700 col-span-3"
          >
            <option value="BAIXA">Baixa</option>
            <option value="MEDIA">Média</option>
            <option value="ALTA">Alta</option>
          </select>
        </div>
      </div>
      <Button className="bg-green-500" type="submit">Salvar</Button>
    </form>
  );
}
