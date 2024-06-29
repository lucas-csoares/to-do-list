import { useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { updateTask } from "@/api/fetch-tasks";
import { DialogTarefaProps, Task } from "@/types/task-types";

export function FormEdicaoTarefa({ task }: DialogTarefaProps) {
  const [titulo, setTitulo] = useState(task.titulo);
  const [prioridade, setPrioridade] = useState(task.prioridade);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    
    const updatedTask = {
      titulo,
      prioridade
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
