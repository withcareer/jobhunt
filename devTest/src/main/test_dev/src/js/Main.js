import { useState, useEffect } from 'react';
import axios from "axios";

function Main() {
    return (
        <div className="Main">
            {/* <Header /> */}
            <MainContainer></MainContainer>
            {/* <Footer /> */}
        </div>
        
    )
}

function MainContainer() {
    const [message, setMessage] = useState('');

    function callback(str) {
        setMessage(str);
    }

    useEffect(
        () => {
            axios.get('/home')
                .then((res) => {
                    console.log(res.data);
                    callback(res.data);
                })
        }, []
    );
    let tokenId = sessionStorage.getItem("tokenId");
    const [isLogin, setIsLogin] = useState(false);

    useEffect(() => {
      if (sessionStorage.getItem("tokenId") === null) {
         // sessionStorage 에 name 라는 key 값으로 저장된 값이 없다면
         console.log("isLogin ?? :: ", isLogin);
      } else {
         // sessionStorage 에 name 라는 key 값으로 저장된 값이 있다면
         // 로그인 상태 변경
         setIsLogin(true);
         console.log("isLogin ?? :: ", isLogin);
      }
   });

    return (
        <div>
            <a className="menu">{message + '!'}</a>
            <br/>
            {isLogin ? (<li><a className="menu" onClick={() => { sessionStorage.clear() }} href="/">{'로그아웃'}</a></li>)
                  : (<li><a className="menu" href="/login">{'로그인'}</a></li>)}
            <br/>
            <a>{tokenId}</a>
            <br/>
            

        </div>
    )


}

export default Main;