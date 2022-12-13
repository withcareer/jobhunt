import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import "./MyPage.css";
import Header from './Form/Header';
import axios from "axios";

function MyPage() {
  return (
    <div className="MyPageContent">
      <Header />
      <MyPageContent />

      
    </div>
  )
}
function MyPageContent() {

  const [inputData, setInputData] = useState([{
    uno:'',
    email: '',
    nickname:'',
    birth:'',
    phone:'',
    // 채용정보
  }])
  const run = () =>{
    axios
      .get("/mypage", {
        headers: {
            Authorization: `${sessionStorage.getItem("tokenId")}`
        }
    })
      .then((res) => {
        setInputData(res.data)
      })
    }
       
  useEffect(run, [])

  return (
      <div class="metee_mypage_main_wraper_join_style">
          <span class="myPageInfutTitle">이메일</span>
					<input class="myPageInputBox"  id='reg_mb_id' name="id" value={inputData.email} readonly /> 


          <span class="myPageInfutTitle">비밀번호</span>
					<input class="myPageInputBox" type="password"
						name="pass" maxlength="20" required
						placeholder="password-1"/>
				

          <span class="myPageInfutTitle">이름</span>
					<input class="myPageInputBox"  id='reg_mb_id' name="id" value={inputData.nickname} readonly /> 
					

          <span class="myPageInfutTitle">주소</span>
					<input class="myPageInputBox"  id='reg_mb_id' name="id" value="화성시 봉담읍" readonly /> 
					

          <span class="myPageInfutTitle">전화번호</span>
					<input class="myPageInputBox"  id='reg_mb_id' name="id" value={inputData.phone} readonly /> 

          <span class="myPageInfutTitle">내가 찜한 채용공고</span>
					<div className="Card1">
            <div className="c1image">
                <img className="phoneImage"  src="img/job_item.jpg" />
                <img className="phoneImage"  src="img/job_item.jpg" />
                <img className="phoneImage"  src="img/job_item.jpg" />
            </div>
        </div>
          
          <div align="center" class="profile_correction">
				  

          

			  </div>
    </div>
  )
}

export default MyPage;