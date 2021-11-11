import logo from './images/logo.png';
import React from "react";
import InputField from "./InputField";
import SubmitButton from "./Submitbutton";
import UserStore from "./stores/UserStore";
import './Loginform.css'

class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      buttonDisabled: false,
    };
  }
  setInputValue(property, val) {
    val = val.trim();

    if (val.length > 12) {
      return;
    }

    this.setState({
      [property]: val,
    });
  }

  resetForm() {
    this.setState({
      username: "",
      password: "",
      buttonDisabled: false,
    });
  }

  async doLogin() {
    if (!this.state.username) {
      return;
    }
    if (!this.state.password) {
      return;
    }
    this.setState({
      buttonDisabled: true,
    });

    try {
      let res = await fetch("/login", {
        method: "post",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: this.state.username,
          password: this.state.password,
        }),
      });

      let result = await res.json();

      if (result && result.success) {
        UserStore.isLoggedIn = true;
        UserStore.username = result.username;
      } else if (result && result.success === false) {
        this.resetForm();
        alert(result.msg);
      }
    } catch (e) {
      console.log(e);
      this.resetForm();
    }
  }
  render() {
    const {isOpen, close} = this.props;
    return (
        <>
        {isOpen ? (
            

            
      <div className="loginform">
          <div clasName="container">
          <div className="logo">
            <img src={logo} alt="netcha"/>
          </div>
          <div className="login_container">
            <span>Find PW</span>
            <span>Find ID</span>
            <span>Register</span>
          </div>
        </div>
        Login
        <InputField
          type="text"
          placeholder="ID"
          value={this.state.username ? this.state.username : ""}
          onChange={(val) => this.setInputValue("username", val)}
        />
        <InputField
          type="password"
          placeholder="PW"
          value={this.state.password ? this.state.password : ""}
          onChange={(val) => this.setInputValue("password", val)}
        />
        <SubmitButton
          className="submit_btn"
          text="login"
          disabled={this.state.buttonDisabled}
          onClick={() => this.doLogin()}
        />
      </div>
      ) : null }
      </>
    );
  }
}

export default LoginForm;