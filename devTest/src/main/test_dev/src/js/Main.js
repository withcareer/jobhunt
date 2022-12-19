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

    const run = () => {
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
                if (res.data == true) {
                    console.log(res.data);
                    // console.log(ch);
                    alert("환영합니다!")
                    // console.log("headers");
                    // sessionStorage.clear()
                    // window.location.href = "/";
                } else if (res.data == false) {
                    console.log(res.data);
                    // console.log(ch);
                    alert("로그인 유효시간 종료!")
                    // console.log("headers");
                    sessionStorage.clear()
                    window.location.href = "/";
                    // setCh(null)
                } else if (res.data == "noLogin") { console.log("noLogin"); }
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
        Cname: '',
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
            return <ItemBox name={item[0]} state={item[1].state} content={item[1].content} position={item[1].position} plan={item[1].plan} link={item[1].link} img={item[1].img} />
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
            <div className="SearchResultForm">
                {items}
            </div>


        </div>
    )

    // function ItemBox(props) {
    //     return (
    //         <div className="MainBox" onClick={(e) => { jlink(props.link) }}>
    //             <div className="MainName">
    //                 <div className="MainImg"><img src={props.img} alt="Blob URL Image" width={124} height={114}></img></div>
    //                 <div className="Maintxt">
    //                     <span>{props.name}</span>
    //                     <br />
    //                     <span>{props.state}</span>
    //                     <br />
    //                     <span>{props.content}</span>
    //                     <br />
    //                     <span>{props.position}</span>
    //                     <br />
    //                     <span>{props.plan}</span>
    //                 </div>
    //             </div>
    //         </div>
    //     )
    // }

    function ItemBox(props) {
        return (
            <div class="container" onClick={(e) => { jlink(props.link) }}>
                <div class="card u-clearfix">
                    <div class="card-media">
                        <img src={props.img} alt="" class="card-media-img" />
                    </div>

                    <div class="card-body">
                        <h2 class="card-body-heading">{props.name}</h2>
                        <div class="card-body-options">
                            <div class="card-body-option card-body-option-favorite">
                                <svg fill="#9C948A" height="26" viewBox="0 0 24 24" width="26" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M0 0h24v24H0z" fill="none" />
                                    <path d="M16.5 3c-1.74 0-3.41.81-4.5 2.09C10.91 3.81 9.24 3 7.5 3 4.42 3 2 5.42 2 8.5c0 3.78 3.4 6.86 8.55 11.54L12 21.35l1.45-1.32C18.6 15.36 22 12.28 22 8.5 22 5.42 19.58 3 16.5 3zm-4.4 15.55l-.1.1-.1-.1C7.14 14.24 4 11.39 4 8.5 4 6.5 5.5 5 7.5 5c1.54 0 3.04.99 3.57 2.36h1.87C13.46 5.99 14.96 5 16.5 5c2 0 3.5 1.5 3.5 3.5 0 2.89-3.14 5.74-7.9 10.05z" />
                                </svg>
                            </div>
                            <div class="card-body-option card-body-option-share">
                                <svg fill="#9C948A" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M0 0h24v24H0z" fill="none" />
                                    <path d="M18 16.08c-.76 0-1.44.3-1.96.77L8.91 12.7c.05-.23.09-.46.09-.7s-.04-.47-.09-.7l7.05-4.11c.54.5 1.25.81 2.04.81 1.66 0 3-1.34 3-3s-1.34-3-3-3-3 1.34-3 3c0 .24.04.47.09.7L8.04 9.81C7.5 9.31 6.79 9 6 9c-1.66 0-3 1.34-3 3s1.34 3 3 3c.79 0 1.5-.31 2.04-.81l7.12 4.16c-.05.21-.08.43-.08.65 0 1.61 1.31 2.92 2.92 2.92 1.61 0 2.92-1.31 2.92-2.92s-1.31-2.92-2.92-2.92z" />
                                </svg>
                            </div>
                        </div>
                        <br />
                        <div class="card-button card-button-link">
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

                </div>
            </div>
        )
    }
}

export default Main;