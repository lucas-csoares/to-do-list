import { Tabs, TabsContent, TabsList, TabsTrigger } from "../tabs";
import { FormEdicaoTarefa } from "./editar-tarefa-form";
import { FormEdicaoTarefaData } from "./editar-tarefadata-form";
import { FormEdicaoTarefaPrazo } from "./editar-tarefaprazo-form";
import { DialogTarefaProps, Task } from "@/types/task-types";

export default function TaskEditTabs({task}: DialogTarefaProps) {
    return (
      <>
        <Tabs defaultValue="livre" className="w-[400px]">
          <TabsList className="bg-gray-800 text-slate-300">
            <TabsTrigger value="livre">Livre</TabsTrigger>
            <TabsTrigger value="data">Data</TabsTrigger>
            <TabsTrigger value="prazo">Prazo</TabsTrigger>
          </TabsList>
          <TabsContent value="livre">
            <FormEdicaoTarefa task={task}/>
          </TabsContent>
          <TabsContent value="data">
            <FormEdicaoTarefaData task={task}/>
          </TabsContent>
          <TabsContent value="prazo">
            <FormEdicaoTarefaPrazo task={task} />
          </TabsContent>
        </Tabs>
      </>
    );
  }