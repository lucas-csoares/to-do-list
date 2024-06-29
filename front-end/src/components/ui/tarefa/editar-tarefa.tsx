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
import { format } from 'date-fns';
import TaskEditTabs from "./form-edicao-wrapper";

export function DialogTarefa({ task }: DialogTarefaProps) {

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button size="sm" className="bg-slate-900">Editar</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-fill bg-gray-800 text-slate-300">
        <DialogHeader>
          <DialogTitle>Editar tarefa</DialogTitle>
          <DialogDescription>
            Clique em salvar para atualizar sua tarefa.
          </DialogDescription>
        </DialogHeader>
        <TaskEditTabs task={task}/>
      </DialogContent>
    </Dialog>
  );
}
