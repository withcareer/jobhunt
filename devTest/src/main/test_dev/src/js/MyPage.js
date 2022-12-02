import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import axios from "axios";

function MyPage() {
  return (
    <div className="MyPageContent">
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
        console.log(res.data)
      })
    }
       
  console.log(inputData)
  useEffect(run, [])

  return (
    <div>
      asd
      <br/>
      {sessionStorage.getItem("tokenId")}
    </div>
  )
}

export default MyPage;