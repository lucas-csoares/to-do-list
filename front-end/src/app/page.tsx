import TaskFormTabs from "@/components/ui/tarefa/form-wrapper";
import TaskList from "@/components/ui/tarefa/lista-de-tarefa";


export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-8 bg-gradient-to-r from-blue-900 via-gray-900 to-black text-white">
      <div className="w-full max-w-5xl p-6 bg-gray-800 rounded-lg shadow-lg">
        <h1 className="text-4xl font-bold mb-6 text-center">To do List</h1>
        <div className="flex flex-col md:flex-row gap-6">
          <div className="w-full md:w-1/2 p-4 bg-gray-700 rounded-lg">
            <TaskFormTabs />
          </div>
          <div className="w-full md:w-1/2 p-4 bg-gray-700 rounded-lg">
            <TaskList />
          </div>
        </div>
      </div>
    </main>
  );
}
