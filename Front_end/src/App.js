import logo from './images/logo.png';
import ad1 from './images/ad1.png';
import './App.css';
import React from 'react';
import axios from "axios";
import Main from './Main';
import Tops from './Tops';
import UserStore from './stores/UserStore';
import SubmitButton from './Submitbutton';
import {observer} from "mobx-react";
import LoginButton from './loginButton';


class App extends React.Component {
  /*
   * State
   */
  state = {
    topMovies: []
  };

  /*
   *  페이지 컴포넌트 생성
  */
  async componentDidMount(){
    /*
     * 데이터 fetch하기
    */
    const {
      data : {
        data : { movies }
      }// 사이트로부터 데이터 fetch
    } = await axios.get("https://yts-proxy.now.sh/list_movies.json?sort_by=rating");
    
    // fetch한 데이터를 state에 저장
    this.setState({topMovies : movies}) 

    /*
     * 로그인 확인
    */
    try {
      let res = await fetch("/isLoggedIn", {
        method: "post",
        headers: {
          Accpet: "application/json",
          "Content-type" : "application/json",
        },
      });

      // res 데이터를 json 형태로 result에 저장
      let result = await res.json();

      if (result && result.success) {
        UserStore.loading = false;
        UserStore.isLogged = true;
        UserStore.username = result.username;
      } else {
        UserStore.loading = false;
        UserStore.isLogged = false;
      } 
    } catch(e) {
      UserStore.loading = false;
      UserStore.isLogged = false;
    }
  }

  async doLogout() {
    try {
      let res = await fetch("/isLoggedIn", {
        method: "post",
        headers: {
          Accept: "application/json",
          "Content-type" : "application/json",
        },
      });

      let result = await res.json();

      if (result && result.success) {
        UserStore.isLoggedIn = false;
        UserStore.username = "";
      }
    } catch (e) {
      console.log(e);
    }
  }

  /*
   *  페이지 렌더링 
  */
  render() {
    /*
     * state에 저장된 data를 topMovies에 전달
     */
    const { topMovies } = this.state

    if (UserStore.loading) {
     return(
       <div className = "app">
         <div className="container">Loading, please wait...</div>
       </div>
     ); 
    } else {
      if(UserStore.isLoggedIn) {
        return (
          <div className="app">
            <div className="container">
              Welcome {UserStore.username}
              <SubmitButton
                text={"Log Out"}
                disabled={false}
                onClick={() => this.doLogout()}
              />
            </div>
          </div>
        );
      }
    }
    return (
      <div className="App">
        <header className="App-header">
          <div className="Tool-bar">
            <a href="#">커뮤니티</a>
            <a href="#">넷플릭스 랭킹</a>
            <a href="#">왓챠 랭킹</a>
            
          </div>
          <div className="App-logo">
            <a href="./" target="_blank" left="40px">
              <img src={logo} className="App-logo" alt="logo"/>
            </a>
          </div>
        </header>

        <LoginButton/>
        <div className="ad">
            <img src={ad1} className="Ad" width="40%" height="40%"/>
          </div>  
          <Main/>
        
        <section className="container">
          <div className="netflix_movies">
            {
             topMovies.map(movie => (
               <Tops
                key={movie.id}
                id={movie.id}
                title={movie.title}
                poster={movie.medium_cover_image}
                rating={movie.rating}
                />)
              )}
          </div>
        </section>   
      </div>
    );
  }
  
  
}



export default observer( App );
