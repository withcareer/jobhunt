import { useState, useEffect } from 'react';
import Footer from './Form/Footer';
import Header from './Form/Header';
import axios from "axios";
// import news from "./json/news.json"
import "./Main.css";

function Main() {
    return (
        <div className="Main">
            <Header />
            <MainContainer></MainContainer>
        </div>

    )
}

// console.log(news);

function MainContainer() {
    const [message, setMessage] = useState('');

    const [ch, setCh] = useState(null);


    // company 정보가 담겨있는 useState예요
    const [company, setCompany] = useState([]);

    // search 구현을 위한 사용자의 쿼리 입력 값을 담아요
    const [user, setUser] = useState("");

    function callback(str) {
        setMessage(str);
    }

    const run = () =>{
        axios
          .get("/home", {
            headers: {
                Authorization: `${sessionStorage.getItem("tokenId")}`
            }
        })
          .then((res) => {
            console.log(res.data);
            // setCh(res.data)
            // console.log(ch);
            if (res.data == true){
                console.log(res.data);
                // console.log(ch);
                alert("환영합니다!")
                // console.log("headers");
                // sessionStorage.clear()
                // window.location.href = "/";
            }else if(res.data == false){
                console.log(res.data);
                // console.log(ch);
                alert("로그인 유효시간 종료!")
                // console.log("headers");
                sessionStorage.clear()
                window.location.href = "/";
                // setCh(null)
            }else if(res.data == "noLogin"){console.log("noLogin");}
          })
        }
           
      useEffect(run, [])

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

    // 회사 정보를 불러오는 useEffect Hook!
    useEffect(() => {
        axios.get("https://raw.githubusercontent.com/jobhunting-page/jobhunt/main/companyInfo/news.json", {
        })
            .then((res) => {
                setCompany(() => {
                    return res.data;
                })
            });
    }, [])

    const [search, setSearch] = useState('');
    const [data, setData] = useState([{
        Cname:'',
        content: ''
    }]);


    const styleInfo = {
        paddingRight: '7px'
    }

    const jlink = (link, e) => {
        // console.log(link);

        if (link != null) {
            const jobKorea = "https://www.jobkorea.co.kr" + link;
            window.open(jobKorea);
        } else {
            window.location.href = "/";
        }
    }

    const news = Object.entries(company)
    
    const items = news.map((item, key) => {
            if (search === "") {
                return null
            }
            else if (item[0].toLowerCase().includes(search.toLowerCase()) || item[0].toLowerCase().includes(search.toLowerCase())) {
                return <ItemBox name={item[0]} state={item[1].state} content={item[1].content} position={item[1].position} plan={item[1].plan} link={item[1].link} />
            }
    })

    const searchCompany = (event) => {
        setUser(event.target.value);
    }


    return (
        <div className="banner_box">

            <img className="bannerImg" alt="banner_01" src="img/job_banner.PNG" />

            <br></br>
            <span className="banner_text">원하는 회사의 정보를 얻어가세요!</span>
            <span className="banner_text2">Find You Want Company</span>
            <br></br>

            <div className="serach_input">
                <input type="text" placeholder="회사를 입력하세요" onChange={event => { setSearch(event.target.value) }} />
                <button className="search_submit">
                    <i className="fa-solid fa-magnifying-glass"></i>
                </button>
            </div>

            <br />
            {items}

        </div>
    )

    function ItemBox(props) {
        return (
            <div className="MainBox" onClick={(e) => { jlink(props.link) }}>
                <div className="MainName">
                    <span>{props.name}</span>
                    <br />
                    <span>{props.state}</span>
                    <br />
                    <span>{props.content}</span>
                    <br />
                    <span>{props.position}</span>
                    <br />
                    <span>{props.plan}</span>
                </div>
            </div>
        )
    }



}

export default Main;








