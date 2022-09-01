import {Icon, Layout, Menu} from 'antd';
import React, {Component} from 'react';
// import logo from '../logo.svg';
import logo1 from '../logo.png';

import {NavLink, Route, Switch} from "react-router-dom";
import AllBaseRule from "../components/BaseRule/AllBaseRule/AllBaseRule";
import AllRule from "../components/Rule/AllRule";
import WrappedAddRuleForm from "../components/Rule/AddRule";
import WrappedAddBaseRuleForm from "../components/BaseRule/AllBaseRule/AddBaseRule";
import BaseRuleDetail from "../components/BaseRule/AllBaseRule/BaseRuleDetail";
import WrappedEditBaseRuleForm from "../components/BaseRule/AllBaseRule/EditBaseRule";
import RuleDetail from "../components/Rule/RuleDetail";
import WrappedEditRuleForm from "../components/Rule/EditRule";
import AllPolicy from "../components/Policy/AllPolicy";
import PolicyDetail from "../components/Policy/PolicyDetail";
import WrappedAddPolicyForm from "../components/Policy/AddPolicy";
import WrappedEditPolicyForm from "../components/Policy/EditPolicy";
import Dashboard from "../components/Dashboard/Dashboard";

const {Header, Content, Footer, Sider} = Layout;
const {SubMenu} = Menu;

class MainLayout extends Component {

    state = {
        collapsed: false,
    };

    toggle = () => {
        this.setState({
            collapsed: !this.state.collapsed,
        });
    };

    render() {
        return (
            <Layout style={{minHeight: '100vh'}}>
                <Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}
                       style={{boxShadow: '3px 0px 6px 1px rgba(153,153,153,0.39)'}}>
                    <div style={{textAlign: 'center'}}>

                        <NavLink to={{pathname: "/"}}>
                            <img src={logo1} style={{height: '100%', width: '100%'}}
                                 alt=""/>
                        </NavLink>
                    </div>

                    <Menu theme="dark" mode="inline" defaultSelectedKeys={['1']}>
                        <Menu.Item key="1">
                            <NavLink to={{
                                pathname: "/"
                            }}>
                                <Icon type="dashboard"/>
                                <span className="nav-text">Dashboard</span>
                            </NavLink>


                        </Menu.Item>
                        <SubMenu
                            key="sub1"
                            title={
                                <span>
                                    <Icon type="appstore"/>
                                    <span>기본규칙</span>
                                </span>
                            }
                        >
                            <Menu.Item key="2">
                                <NavLink to={{
                                    pathname: "/all-base-rule"
                                }}>
                                    <span style={{paddingLeft: '10px'}}>기본규칙 목록조회</span>
                                </NavLink>
                            </Menu.Item>
                            <Menu.Item key="3">
                                <NavLink to={{
                                    pathname: "/add-base-rule"
                                }}>
                                    <span style={{paddingLeft: '10px'}}>기본규칙 추가</span>
                                </NavLink>

                            </Menu.Item>

                        </SubMenu>

                        <SubMenu
                            key="sub2"
                            title={
                                <span>
                                    <Icon type="appstore"/>
                                    <span>규칙</span>
                                </span>
                            }
                        >
                            <Menu.Item key="4">
                                <NavLink to={{
                                    pathname: "/all-rule"
                                }}>
                                    <span style={{paddingLeft: '10px'}}>규칙 목록조회</span>
                                </NavLink>
                            </Menu.Item>
                            <Menu.Item key="5">
                                <NavLink to={{
                                    pathname: "/add-rule"
                                }}>
                                    <span style={{paddingLeft: '10px'}}>규칙 추가</span>
                                </NavLink>

                            </Menu.Item>

                        </SubMenu>

                        <SubMenu
                            key="sub3"
                            title={
                                <span>
                                    <Icon type="appstore"/>
                                    <span>정책</span>
                                </span>
                            }
                        >
                            <Menu.Item key="6">
                                <NavLink to={{
                                    pathname: "/all-policy"
                                }}>
                                    <span style={{paddingLeft: '10px'}}>정책 목록조회</span>
                                </NavLink>
                            </Menu.Item>
                            <Menu.Item key="7">
                                <NavLink to={{
                                    pathname: "/add-policy"
                                }}>
                                    <span style={{paddingLeft: '10px'}}>정책 추가</span>
                                </NavLink>
                            </Menu.Item>
                        </SubMenu>

                        {/*<SubMenu
                            key="sub4"
                            title={
                                <span>
                                    <Icon type="database"/>
                                    <span className="nav-text">하둡 파일 이동</span>
                                </span>
                            }
                        >
                            <Menu.Item key="8">
                                <NavLink to={{
                                    pathname: "/add-hdfs"
                                }}>
                                    <span style={{paddingLeft: '10px'}}>하둡 파일 이동</span>
                                </NavLink>
                            </Menu.Item>

                            <Menu.Item key="9">
                                <NavLink to={{
                                    pathname: "/delete-hdfs"
                                }}>
                                    <span style={{paddingLeft: '10px'}}>하둡 파일 삭제</span>
                                </NavLink>
                            </Menu.Item>
                        </SubMenu>*/}

                    </Menu>
                </Sider>

                <Layout>
                    <Header style={{
                        background: '#fff', paddingLeft: '24px',
                        // boxShadow: '0px 5px 6px 1px rgba(153,153,153,1)'
                        boxShadow: '0 1px 4px rgba(0, 21, 41, 0.08)'
                    }}>
                        <Icon
                            className="trigger"
                            type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
                            onClick={this.toggle}/>

                        <span style={{marginLeft: '24px', fontWeight: 'bold', fontSize: '20px'}}>Smart City Data Life Cycle Management Systems</span>

                        {/*<Menu
                            theme="light"
                            mode="horizontal"
                            defaultSelectedKeys={['2']}
                            style={{lineHeight: '64px', display: "inline-block", position: 'absolute', right: 0}}>

                            <Menu.Item key="1">nav 1</Menu.Item>
                            <Menu.Item key="2">nav 2</Menu.Item>
                            <Menu.Item key="3">nav 3</Menu.Item>
                        </Menu>*/}
                    </Header>

                    <Content style={{margin: '24px 16px 0'}}>
                        <div style={{padding: 24, background: '#fff', minHeight: 360}}>

                            <div>
                                <Route path={"/"} exact={true} component={Dashboard}/>
                            </div>

                            <Switch>
                                <Route path={"/all-base-rule"} exact={true} component={AllBaseRule}/>
                                <Route path={"/add-base-rule"} exact={true} component={WrappedAddBaseRuleForm}/>
                                <Route path={"/all-rule"} exact={true} component={AllRule}/>
                                <Route path={"/add-rule"} exact={true} component={WrappedAddRuleForm}/>
                                <Route path={"/all-policy"} exact={true} component={AllPolicy}/>
                                <Route path={"/add-policy"} exact={true} component={WrappedAddPolicyForm}/>
                                {/*<Route path={"/add-hdfs"} exact={true} component={WrappedAddHdfsForm}/>*/}
                                {/*<Route path={"/delete-hdfs"} exact={true} component={WrappedDeleteHdfsForm}/>*/}


                                <Route path={"/base-rule/detail/:id"} exact={true} component={BaseRuleDetail}/>
                                <Route path={"/base-rule/edit/:id"} exact={true} component={WrappedEditBaseRuleForm}/>

                                <Route path={"/rule/detail/:id"} exact={true} component={RuleDetail}/>
                                <Route path={"/rule/edit/:id"} exact={true} component={WrappedEditRuleForm}/>

                                <Route path={"/policy/detail/:id"} exact={true} component={PolicyDetail}/>
                                <Route path={"/policy/edit/:id"} exact={true} component={WrappedEditPolicyForm}/>
                            </Switch>

                        </div>
                    </Content>
                    <Footer style={{textAlign: 'center'}}>CBNU ©2019 Created by CBNU-Big Data Lab & Blockchain</Footer>
                </Layout>
            </Layout>
        );
    }
}

export default MainLayout;
