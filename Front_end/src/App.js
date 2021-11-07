import logo from './images/logo.png';
import ad1 from './images/ad1.png';
import './App.css';
import React from 'react';
import axios from "axios";
import Main from './Main';
import Tops from './Tops';

class App extends React.Component {
  state = {
    topMovies: []
  };
  async componentDidMount(){
    const {
      data : {
        data : { movies }
      }// 사이트로부터 데이터 fetch
    } = await axios.get("https://yts-proxy.now.sh/list_movies.json?sort_by=rating");
    
    
    // fetch한 데이터를 state에 저장
    this.setState({topMovies : movies})
  }
  render() {
    const { topMovies } = this.state
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
          
          <div className="ad">
            <img src={ad1} className="Ad" width="40%" height="40%"/>
          </div>  
          <Main/>
          
        </header>
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



export default App;
