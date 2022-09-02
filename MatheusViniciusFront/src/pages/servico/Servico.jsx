import React, {useEffect, useState} from "react";
import './Servico.css';
import axios from "axios";


export default function ServicoFunction() {
 
    const [servico, setServico] = useState({ 
        nomeCliente: null, descricao: "Descrição", valorServico: null,
        valorPago: null, status: null, dataInicio: null, 
        dataTermino: null, dataPagamento: null});
       const [servicos, setServicos] = useState([]);
       const [atualizar, setAtualizar] = useState({});

       useEffect(() => {
        //O que será executado
        // axios.get("http://localhost:8080/api/servico").then(result=> {
        //     // console.log(result.data.content);
        //     setServicos(result.data.content);
        // });
       }, [atualizar/**Variaveis de alteração */])
 
    
    function handleChange(event){
        setServico({...servico,[event.target.name]:event.target.value});
    }

    function handleSubmit(event){
        event.preventDefault();

        if (servico.id == undefined) {
            axios.post("http://localhost:8080/api/servico/", servico).then(result => {

                setAtualizar(result.data);
                //Atualizara nossa tabela
            });
        } else {
            axios.put("http://localhost:8080/api/servico/", servico).then(result => {

                setAtualizar(result.data);
                //Atualizara nossa tabela
            });
        }
        
    }

    function excluir(id) {
        axios.delete("http://localhost:8080/api/servico/"+id).then(() => {
            setAtualizar(id);
        });
    }

    function pagamentosPendentes() {
        axios.get("http://localhost:8080/api/servicosStatus/" + "pendente").then(result => {
            // console.log(result.data.content);
            setServicos(result.data.content);
        });
    }

    function servicosCancelados() {
        axios.get("http://localhost:8080/api/servicosStatus/" + "cancelado").then(result => {
            // console.log(result.data.content);
            setServicos(result.data.content);
        });
    }

    function listarTodos() {
        axios.get("http://localhost:8080/api/servico").then(result => {
            // console.log(result.data.content);
            setServicos(result.data.content);
        });
    }
    
        return (
            <div>
               <h1>Cadastrar servico</h1>
                
                <form onSubmit={handleSubmit}>
                    <div className="col-6">
                        <div className="col-6">
                            <label className="form-label">Nome do Cliente</label>
                            <input type="text" className="form-control" id="nomeCliente"
                                name="nomeCliente" value={servico.nomeCliente} onChange={handleChange} />
                        </div>
                        <br />
                        <div className="col-6">
                            <label className="form-label">Data inicio</label>
                            <input type="date" className="form-control" id="dataInicio"
                               name="dataInicio" value={servico.dataInicio} onChange={handleChange} />
                        </div>
                        <br />
                        <div className="col-6">
                            <label className="form-label">Data termino</label>
                            <input type="date" className="form-control" id="dataTermino"
                                name="dataTermino" value={servico.dataTermino} onChange={handleChange} />
                        </div>
                        <br />
                        <div className="col-6">
                            <label className="form-label">Descrição do serviço</label>
                            <input type="text" className="form-control" id="descricao"
                                name="descriacao" value={servico.descricao} onChange={handleChange} />
                        </div>
                        <br />
                        <div className="col-6">
                            <label className="form-label">Valor do serviço</label>
                            <input type="number" className="form-control" id="valorServico"
                                name="valorServico" value={servico.valorServico} onChange={handleChange} />
                        </div>
                        <br />
                        <div className="col-6">
                            <label className="form-label">Valor pago</label>
                            <input type="number" className="form-control" id="valorPago"
                                name="valorPago" value={servico.valorPago} onChange={handleChange} />
                        </div>
                        <br />
                        <div className="col-6">
                            <label className="form-label">Data pagamento</label>
                            <input type="date" className="form-control" id="dataPagamento"
                                name="dataPagamento" value={servico.dataPagamento} onChange={handleChange} />
                        </div>
                        <br />
                    </div>
                    
                    <br/>
                    
                    <input type="submit" className="btn btn-primary btn-success" value="Cadastrar" />
                    <br /><br /> <br />
                    
                </form>
                <div className="row">
                    <button onClick={() => listarTodos()} className="btn btn-primary botao">Listar todos</button> &nbsp;&nbsp;
                    <button onClick={() => pagamentosPendentes()} className="btn btn-primary botao">Pagamentos pendentes</button> &nbsp;&nbsp;
                    <button onClick={() => servicosCancelados()} className="btn btn-primary botao">Servicos cancelados</button>
                </div>
                <br/>
                <br/>

                <table className="table  table-dark table-striped">
                    <thead>
                        <tr>
                            <td>id</td>
                            <td>Nome cliente</td>
                            <td>Valor serviço</td>
                            <td>Valor pago</td>
                            <td>Data inicio</td>
                            <td>Data termino</td>
                            <td>Opções</td>
                        </tr>
                    </thead>
                    <tbody>
                        {servicos.map(user=>
                        <tr key={servico.id}>
                            <td>{servico.id}</td>
                            <td>{servico.nomeCliente}</td>
                            <td>{servico.valorServico}</td>
                            <td>{servico.dataInicio}</td>
                            <td>{servico.dataTermino}</td>
                            <td>{servico.valorPago}</td>
                            
                            <td >
                                <button onClick={() => setServico(servico)} className="btn btn-primary">Alterar </button> &nbsp;&nbsp;
                                <button onClick={() => excluir(servico.id)} className="btn btn-primary btn-danger">Excluir</button>
                            </td>
                        </tr>
                        )}
                    </tbody>
                </table>

            </div>
        );
}