import { Tabs, TabsContent, TabsList, TabsTrigger } from "../tabs";
import FormData from "./tarefa-data-form";
import Form from "./tarefa-form";
import FormPrazo from "./tarefa-prazo-form";

export default function TaskFormTabs() {
    return (
      <>
        <Tabs defaultValue="livre" className="w-[400px]">
          <TabsList className="bg-gray-800 text-slate-300">
            <TabsTrigger value="livre">Livre</TabsTrigger>
            <TabsTrigger value="data">Data</TabsTrigger>
            <TabsTrigger value="prazo">Prazo</TabsTrigger>
          </TabsList>
          <TabsContent value="livre">
            <Form />
          </TabsContent>
          <TabsContent value="data">
            <FormData />
          </TabsContent>
          <TabsContent value="prazo">
            <FormPrazo />
          </TabsContent>
        </Tabs>
      </>
    );
  }