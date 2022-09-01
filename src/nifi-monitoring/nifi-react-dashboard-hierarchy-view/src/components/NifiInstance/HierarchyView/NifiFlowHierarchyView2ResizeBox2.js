import 'rc-tree/assets/index.css';
import React from 'react';
import Tree, {TreeNode} from 'rc-tree';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faLayerGroup} from '@fortawesome/free-solid-svg-icons'
import axios from "../../../axios-spring-boot-api-instance";
import * as endpoint from "../../../Endpoints";
import Aux from "../../../hoc/Aux/Aux";
import NifiFlowDetail from "./NifiFlowDetail";
// import {Row, RowsWrapper} from "react-grid-resizable/lib";
// import {Card, Col, Row} from "antd";
import {Col as ColRe, ColsWrapper, Row as RowRe, RowsWrapper} from "react-grid-resizable/lib";
import {Spin} from "antd";
// import Col from "antd/lib/descriptions/Col";

const element = <FontAwesomeIcon icon={faLayerGroup}/>;
let LEVEL = 1;
const spin = (
    <div style={{
        width: "inherit", height: "inherit",
        zIndex: 1, position: "absolute",
        backgroundColor: "rgba(0, 0, 0, 0.2)"
    }}><Spin style={{paddingTop: '200px', zIndex: 2}} tip="Loading..."> </Spin></div>
);

function setLeaf(treeData, curKey, level) {
    console.log("Tree data set leaf");
    console.log(treeData);
    const loopLeaf = (data, lev) => {
        const l = lev - 1;
        data.forEach((item) => {
            if ((item.key.length > curKey.length) ? item.key.indexOf(curKey) !== 0 :
                curKey.indexOf(item.key) !== 0) {
                return;
            }
            if (item.children) {
                loopLeaf(item.children, l);
            } else if (l < 1) {
                item.isLeaf = true;
            }
        });
    };
    loopLeaf(treeData, level + 1);
}

function getNewTreeData(treeData, curKey, child, level) {
    console.log("oh my god");
    const loop = (data) => {
        if (level < 1 || curKey.length - 3 > level * 2) return;
        data.forEach((item) => {
            if (curKey.indexOf(item.key) === 0) {
                if (item.children) {
                    loop(item.children);
                } else {
                    item.children = child;
                }
            }
        });
    };
    loop(treeData);
    setLeaf(treeData, curKey, level);

    return 1;
}

/*Display all nifi Process Group in Hierarchy Version 4(Added Resizable component but use another resizable library)*/
class NifiFlowHierarchyView2ResizeBox2 extends React.Component {
    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            treeData: [],
            checkedKeys: [],
            rootFlow: {},
            childToAppend: [],
            flowDetail: null,
            isSpin: false,
            isSpinDetail: false,
            loadingDetail: false,
        };
    }
    componentDidMount() {
        let rcTreeHeight = document.getElementsByClassName('rc-tree')[0].clientHeight;
        console.log("rc tree ", rcTreeHeight);

        // Make a request for a user with a given ID
        axios.get(endpoint.FLOW_ROOT)
            .then(res => {
                console.log(res);
                setTimeout(() => {
                    this.setState({
                        treeData: [
                            {name: res.data.data.flow.flowInfo.component.name, key: '0-0', id: res.data.data.id},
                        ],
                        checkedKeys: ['0-0'],
                        rootFlow: res.data.data.flow,
                    });
                }, 100);

                this.onLoadDataInitial(res.data.data.id);

                axios.get(endpoint.FLOW_BY_ID + res.data.data.id)
                    .then(res => {
                        console.log(res);
                        this.setState({flowDetail: res.data.data});
                        console.log(this.state.flowDetail.flow);
                    });

            })
            .catch(error => {
                console.log(error)
            })
            .finally(res => {
                console.log(res)
            });
    }
    componentDidUpdate(prevProps, prevState, snapshot) {
        let rcTreeHeight = document.getElementsByClassName('rc-tree')[0].clientHeight;
        console.log("rc tree ", rcTreeHeight);
    }

    generateTreeNodes = () => {
        // const key = treeNode.props.eventKey;
        LEVEL++;
        const arr = [];
        // console.log("this is key: " + key);
        if (this.state.childToAppend)
            for (let i = 0; i < this.state.childToAppend.length; i++) {
                arr.push({
                    name: this.state.childToAppend[i].name,
                    key: `${this.state.childToAppend[i].key}-${i}`,
                    id: this.state.childToAppend[i].id
                });
            }

        console.log('arr');
        console.log(arr);
        return arr;
    };
    onSelect = (info, fullInfo) => {
        // this.setState({})
        this.setState({isSpinDetail: true});
        console.log('selected', info);
        console.log('ID: ' + fullInfo.node.props.id);
        let processGroupId = fullInfo.node.props.id;

        axios.get(endpoint.FLOW_BY_ID + processGroupId)
            .then(res => {
                console.log(res);
                this.setState({flowDetail: res.data.data});
                console.log(this.state.flowDetail.flow);
                this.setState({isSpinDetail: false});
            });
    };

    onLoadData = (treeNode) => {
        this.setState({isSpin: true});
        console.log('on load data...');
        console.log(treeNode);
        let updatedData = [];
        this.setState({childToAppend: []});

        axios.get(endpoint.FLOW_BY_ID + treeNode.props.id)
            .then(res => {
                if (res.data.data.flow.processGroups.length > 0)
                    updatedData = res.data.data.flow.processGroups.map(processGroup => {
                        return {
                            name: processGroup.component.name,
                            key: treeNode.props.pos,
                            id: processGroup.id
                        }
                    });
                this.setState({childToAppend: updatedData});
            });

        if (this.state.childToAppend) {
            return new Promise((resolve) => {
                setTimeout(() => {
                    const treeData = [...this.state.treeData];
                    console.log("Tree Data");
                    console.log(treeData);
                    console.log(this.state.treeData);

                    getNewTreeData(treeData, treeNode.props.eventKey, this.generateTreeNodes(treeNode), LEVEL);
                    this.setState({treeData});
                    resolve();
                    this.setState({isSpin: false});
                }, 1500);
            });
        }
    };

    onLoadDataInitial = (id) => {
        this.setState({isSpin: true});
        console.log('on load data...');
        let updatedData = [];
        this.setState({childToAppend: []});

        axios.get(endpoint.FLOW_BY_ID + id)
            .then(res => {
                if (res.data.data.flow.processGroups.length > 0)
                    updatedData = res.data.data.flow.processGroups.map(processGroup => {
                        return {
                            name: processGroup.component.name,
                            key: '0-0',
                            id: processGroup.id
                        }
                    });
                this.setState({childToAppend: updatedData});
            });
        if (this.state.childToAppend) {
            return new Promise((resolve) => {
                console.log("Resolve");
                console.log(resolve);
                setTimeout(() => {
                    const treeData = [...this.state.treeData];
                    console.log("Tree Data");
                    console.log(treeData);
                    console.log(this.state.treeData);

                    getNewTreeData(treeData, '0-0', this.generateTreeNodes(), LEVEL);
                    this.setState({treeData});

                    console.log("tree data");
                    console.log(treeData);

                    resolve();
                    this.setState({isSpin: false});
                }, 1500);
            });
        }

    };

    render() {
        // let elHeight = document.body.clientHeight;

        // console.log("This is meme height 21");
        // console.log(elHeight);


        const loop = (data) => {
            return data.map((item) => {

                if (item.children) {
                    return (
                        <TreeNode id={item.id} style={{margin: '10px 0px 10px 1px'}} icon={element} checkable={false}
                                  title={item.name}
                                  key={item.key}>{loop(item.children)}>
                        </TreeNode>
                    );
                }
                return (
                    <TreeNode id={item.id} style={{margin: '10px 0px 10px 1px'}} icon={element} checkable={false}
                              title={item.name}
                              key={item.key}
                              isLeaf={item.isLeaf}/>
                );
            });
        };
        const treeNodes = loop(this.state.treeData);

        let flowDetail = null;

        if (this.state.flowDetail !== null) {
            flowDetail = <NifiFlowDetail spin={this.state.isSpinDetail} flow={this.state.flowDetail}/>;
        } else
            flowDetail = 'Click on process group to show flow detail.';

        return (
            <Aux>
                <div style={{background: '#fff', minHeight: 400, overflow: "scroll"}} id="meme">
                    <RowsWrapper>
                        <RowRe
                            // initialHeight={document.body.clientHeight}
                            initialHeight={window.outerHeight}
                            // initialHeight={100}
                        >
                            <ColsWrapper
                                separatorProps={{
                                    style: {
                                        backgroundColor: 'gray',
                                        width: '2px'
                                        // border: '1px solid red'
                                    }
                                }}>

                                <ColRe style={{minWidth: '200px !important', overflow: 'scroll'}}
                                       initialWidth={200}>

                                    {this.state.isSpin ? spin : ""}

                                    <Tree
                                        onSelect={this.onSelect}
                                        // onCheck={this.onCheck} checkedKeys={this.state.checkedKeys}
                                        loadData={this.onLoadData}

                                        defaultExpandAll={false}
                                        defaultSelectedKeys={['0-0']}
                                        defaultExpandedKeys={['0-0']}
                                        defaultExpandParent={false}
                                    >
                                        {treeNodes}

                                    </Tree>
                                </ColRe>

                                <ColRe style={{minWidth: '200px !important', overflow: 'scroll'}}>
                                    <RowsWrapper>
                                        <RowRe>
                                            <ColsWrapper>
                                                <ColRe style={{
                                                    minWidth: '200px !important',
                                                    width: window.outerWidth,
                                                    overflow: 'scroll',
                                                }}>
                                                    {/*{this.state.isSpinDetail ? spinDetail : ""}*/}
                                                    {flowDetail}

                                                </ColRe>
                                            </ColsWrapper>

                                        </RowRe>
                                    </RowsWrapper>
                                </ColRe>

                            </ColsWrapper>
                        </RowRe>
                    </RowsWrapper>
                </div>
            </Aux>
        );
    }
}

export default NifiFlowHierarchyView2ResizeBox2;

