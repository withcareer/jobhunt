import { useState, useEffect } from 'react';
import Footer from './Form/Footer';
import Header from './Form/Header';
import axios from "axios";
import "./Main.css";

function Main() {
    return (
        <div className="Main">
            <Header />
            <MainContainer></MainContainer>
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
        <div className="banner_box">

        <img className="bannerImg" alt="banner_01" src="img/job_banner.PNG" />

            <br></br>
        <span class="banner_text">원하는 회사의 정보를 얻어가세요!</span>
        <span class="banner_text2">Find You Want Company</span>
        <br></br>

        <div class="serach_input">
			<input type="text" placeholder="회사를 입력하세요"/>
			<button class="search_submit">
				<i class="fa-solid fa-magnifying-glass"></i>
			</button>
		</div>

            
        </div>
    )


}

export default Main;