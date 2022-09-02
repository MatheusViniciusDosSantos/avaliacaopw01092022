import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Header from './components/header/Header';
import Footer from './components/footer/Footer';
import Servico from './pages/servico/Servico';

function App() {
  return (
    <Router>
      <div className='Container'>
        <Header nome="Cadastro de Serviços"/>
        <Routes>
          <Route path='/' element={<Servico />} />
        </Routes>
        <Footer/>
      </div>
    </Router>
  );
}

export default App;
