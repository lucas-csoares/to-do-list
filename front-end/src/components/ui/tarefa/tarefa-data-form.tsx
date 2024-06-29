'use client'
import { handleSubmitData } from '@/handlers/handleSubmit';
import React, { useState } from 'react';

const FormData = () => {
    const [titulo, setTitulo] = useState("");
    const [dataPrevisao, setDataPrevisao] = useState("");
    const [prioridade, setPrioridade] = useState("");

  return (
    <div className="max-w-md mx-auto bg-gray-800 p-8 rounded-lg">
      <h2 className="text-2xl mb-4 text-slate-300">Criar Tarefa</h2>
      <form onSubmit={(e) => handleSubmitData(e, titulo, prioridade, dataPrevisao)}>
        <label htmlFor="titulo" className="block mb-2 text-slate-300">Título:</label>
        <input
          type="text"
          id="titulo"
          name="titulo"
          value={titulo}
          onChange={(e) => setTitulo(e.target.value)}
          className="w-full p-2 mb-4 bg-gray-700 rounded"
        />

        <label htmlFor="dataPrevisao" className="block mb-2 text-slate-300">Data:</label>
        <input
          type="date"
          id="dataPrevisao"
          name="dataPrevisao"
          value={dataPrevisao}
          onChange={(e) => setDataPrevisao(e.target.value)}
          className="w-full p-2 mb-4 bg-gray-700 rounded"
        />

        <label htmlFor="prioridade" className="block mb-2 text-slate-300">Prioridade:</label>
        <select
          id="prioridade"
          name="prioridade"
          value={prioridade}
          onChange={(e) => setPrioridade(e.target.value)}
          className="w-full p-2 mb-4 bg-gray-700 rounded text-slate-300"
        >
          <option value="BAIXA">Baixa</option>
          <option value="MEDIA">Média</option>
          <option value="ALTA">Alta</option>
        </select>

        <button type="submit" className="w-full p-2 bg-green-500 hover:bg-green-600 rounded">
          Enviar
        </button>
      </form>
    </div>
  );
};

export default FormData;
