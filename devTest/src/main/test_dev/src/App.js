import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import "./reset.css"
import Main from './js/Main.js';
import Login from './js/Auth/Login.js'
import Join from './js/Auth/Join';
import MyPage from './js/MyPage';


function App() {


  return (
    <BrowserRouter>
      <Routes>
        <Route path = '/jobhunt/v1/Users/' element = {<Main/>}/>
        <Route path='/jobhunt/v1/Users/login' element={<Login />}/>
        <Route path='/jobhunt/v1/Users/join' element={<Join />}/>
        <Route path='/jobhunt/v1/Users/myPage' element={<MyPage />}/>

      </Routes>
    </BrowserRouter>
  );
}




export default App;