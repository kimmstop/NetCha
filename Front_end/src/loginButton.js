import React, { Component } from "react";
import LoginForm from "./Loginform";

class LoginButton extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isModalOpen: false,
    };
  }

  openModal = () => {
    this.setState({ isModalOpen: true });
  };

  closeModal = () => {
    this.setState({ isModalOpen: false });
  };

  render() {
    return (
      <>
        <a onClick={this.openModal}>로그인하기</a>
        <LoginForm isOpen={this.state.isModalOpen} close={this.closeModal} />
      </>
    );
  }
}

export default LoginButton;