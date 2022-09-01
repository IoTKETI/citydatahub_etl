import React, {Component} from 'react';
import {NavLink, Route, Switch} from "react-router-dom";
import logo from '../../logo.svg';
import Aux from '../Aux/Aux';

import NifiProcessorDetail from "../../components/NifiProcessor/NifiProcessorDetail/NifiProcessorDetail";
import NifiConnectionDetail from "../../components/NifiConnection/NifiConnectionDetail/NifiConnectionDetail";
import NifiProcessGroup from "../../components/NifiProcessGroup/NifiProcessGroup";


import {Layout, Menu} from 'antd';
import Dashboard from "../../components/Dashboard/Dashboard";
import {faCube, faCubes, faTachometerAlt, faThList, faUpload} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import NifiRegistryBucket from "../../components/NifiRegistry/NifiRegistryBucket/NifiRegistryBucket";
import NifiRegistryItem from "../../components/NifiRegistry/NifiRegistryItem/NifiRegistryItem";
import NifiFlowHierarchyView2ResizeBox2
    from "../../components/NifiInstance/HierarchyView/NifiFlowHierarchyView2ResizeBox2";
import {faGitAlt} from "@fortawesome/free-brands-svg-icons";
import WrappedUploadTemplateForm from "../../components/NifiTemplate/UploadTemplate";
import SearchProcessor from "../../components/SearchProcessor/SearchProcessor";

const {Header, Content, Footer, Sider} = Layout;
const {SubMenu} = Menu;

/*
* Main Layout
* */
class MainLayout extends Component {
    state = {
        collapsed: false,
    };

    onCollapse = collapsed => {
        console.log(collapsed);
        this.setState({collapsed});
    };

    render() {

        var headerStyle = {
            padding: 0
        };

        return (
            <Aux>
                <Layout style={{minHeight: '100vh'}}>

                    {/*Sidebar Menu*/}
                    <Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}>
                        <div className="logo"/>
                        <NavLink to={{pathname: "/"}}><img src={logo} style={{height: '66px', width: '100%'}}
                                                           alt=""/></NavLink>
                        {/*<Menu theme="dark" defaultSelectedKeys={['1']} mode="inline"*/}
                        <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline"
                              style={{borderTop: '4px solid #1890ff'}}>
                            <Menu.Item key="1" style={{marginTop: '0px'}}>
                                <NavLink to={{
                                    pathname: "/"
                                }}>
                                    <FontAwesomeIcon icon={faTachometerAlt} size="lg"/>
                                    <span style={{paddingLeft: '10px'}}>Dashboard</span>
                                </NavLink>
                            </Menu.Item>

                            {/*<Menu.Item key="2" style={{marginTop: '0px'}}>
                                <NavLink to={{
                                    pathname: "/instance"
                                }}>
                                    <FontAwesomeIcon icon={faThList} size="lg"/>
                                    <span style={{paddingLeft: '10px'}}>Nifi Flows</span>
                                </NavLink>
                            </Menu.Item>*/}

                            {/*<Menu.Item key="3" style={{marginTop: '0px'}}>
                                <NavLink to={{
                                    pathname: "/instance-hierarchy-view"
                                }}>
                                    <FontAwesomeIcon icon={faThList} size="lg"/>
                                    <span style={{paddingLeft: '10px'}}>Hierarchy View Flow V1</span>
                                </NavLink>
                            </Menu.Item>*/}

                            {/*<Menu.Item key="4" style={{marginTop: '0px'}}>
                                <NavLink to={{
                                    pathname: "/instance-hierarchy-view-2"
                                }}>
                                    <FontAwesomeIcon icon={faThList} size="lg"/>
                                    <span style={{paddingLeft: '10px'}}>Hierarchy View Flow V2</span>
                                </NavLink>
                            </Menu.Item>*/}

                            <Menu.Item key="2" style={{marginTop: '0px'}}>
                                <NavLink to={{
                                    pathname: "/instance-hierarchy-view-2-resize-box2"
                                }}>
                                    <FontAwesomeIcon icon={faThList} size="lg"/>
                                    <span style={{paddingLeft: '10px'}}>Nifi Processors</span>
                                </NavLink>
                            </Menu.Item>

                            <Menu.Item key="3" style={{marginTop: '0px'}}>
                                <NavLink to={{
                                    pathname: "/search-processor"
                                }}>
                                    <FontAwesomeIcon icon={faThList} size="lg"/>
                                    <span style={{paddingLeft: '10px'}}>Search Processors</span>
                                </NavLink>
                            </Menu.Item>

                            <Menu.Item key="4" style={{marginTop: '0px'}}>
                                <NavLink to={{
                                    pathname: "/nifi-upload-template"
                                }}>
                                    {/*<FontAwesomeIcon icon={faFileUpload} size="lg"/>*/}
                                    <FontAwesomeIcon icon={faUpload} size="lg"/>
                                    <span style={{paddingLeft: '10px'}}>Upload Template</span>
                                </NavLink>
                            </Menu.Item>


                            {/*<Menu.Item key="6" style={{marginTop: '0px'}}>
                                <NavLink to={{
                                    pathname: "/instance-hierarchy-view-2-resize-box2"
                                }}>
                                    <FontAwesomeIcon icon={faThList} size="lg"/>
                                    <span style={{paddingLeft: '10px'}}>Hierarchy View Flow v2</span>
                                </NavLink>
                            </Menu.Item>*/}

                            <SubMenu
                                key="sub1"
                                title={<span>
                                        <FontAwesomeIcon icon={faGitAlt} size="lg"/>
                                        <span style={{paddingLeft: '10px'}}>Nifi Registry</span>
                                    </span>
                                }>

                                <Menu.Item key="7">
                                    <NavLink to={{
                                        pathname: "/nifi-registry-bucket"
                                    }}>
                                        <FontAwesomeIcon icon={faCubes} size="lg"/>
                                        <span style={{paddingLeft: '10px'}}>Buckets</span>
                                    </NavLink>
                                </Menu.Item>

                                <Menu.Item key="8">
                                    <NavLink to={{
                                        pathname: "/nifi-registry-item"
                                    }}>
                                        <FontAwesomeIcon icon={faCube} size="lg"/>
                                        <span style={{paddingLeft: '10px'}}>Items</span>
                                    </NavLink>
                                </Menu.Item>
                            </SubMenu>
                        </Menu>
                    </Sider>
                    {/*End Sidebar Menu*/}

                    <Layout>
                        <Header className="header" style={headerStyle}>

                            <div className="logo"/>

                            <Menu
                                theme="dark"
                                mode="horizontal"
                                defaultSelectedKeys={['2']}
                                style={{lineHeight: '60px', borderBottom: '4px solid #1890ff'}}
                            >
                                <Menu.Item disabled={true} key="1" style={{padding: 0}}>
                                    <span style={{fontWeight: 'bold', fontSize: '30px'}}>Nifi Monitoring System</span>
                                </Menu.Item>
                            </Menu>
                        </Header>

                        <Content style={{margin: '22px 16px 16px 16px'}}>

                            <div>

                                <Route path="/" component={Dashboard} exact={true}/>

                                <Switch>
                                    <Route path="/instance-hierarchy-view-2-resize-box2"
                                           component={NifiFlowHierarchyView2ResizeBox2} exact={true}/>

                                    <Route path="/search-processor"
                                           component={SearchProcessor} exact={true}/>

                                    <Route path="/nifi-upload-template" component={WrappedUploadTemplateForm}
                                           exact={true}/>

                                    <Route path="/nifi-registry-bucket" component={NifiRegistryBucket} exact={true}/>
                                    <Route path="/nifi-registry-item" component={NifiRegistryItem} exact={true}/>

                                    <Route path="/component-detail/processors/:id" component={NifiProcessorDetail}
                                           exact/>
                                    <Route path="/component-detail/connections/:id" component={NifiConnectionDetail}
                                           exact/>
                                    <Route path="/component-detail/process-groups/:id" component={NifiProcessGroup}
                                           exact/>
                                </Switch>
                            </div>
                        </Content>
                        <Footer style={{textAlign: 'center'}}>Nifi Monitoring Systems ©2018 Created by CBNU Big Data
                            Lab</Footer>

                        {/*<h1>API_URL: {window._env_.API_URL}</h1>*/}
                        {/*<h1>API_PORT: {window._env_.API_PORT}</h1>*/}
                    </Layout>
                </Layout>
            </Aux>
        );
    }
}

export default MainLayout;
