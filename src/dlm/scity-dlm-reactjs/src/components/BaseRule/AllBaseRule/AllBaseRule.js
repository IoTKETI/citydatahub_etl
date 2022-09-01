import React, {Component} from 'react';
import Aux from '../../../layouts/Aux';
import {Breadcrumb, Button, Col, Divider, message, Popconfirm, Row, Table, Tag, Tooltip, Icon} from 'antd';
import * as endpoint from '../../../utilities/Endpoints';
import axios from '../../../utilities/axios-spring-boot-api-instance'
import {NavLink} from "react-router-dom";

class AllBaseRule extends Component {

    constructor() {
        super();
        this.state = {
            allBaseRule: [],
        };
        this.columns = [
            {
                width: '90px',
                title: 'No',
                dataIndex: 'no',
                key: 'no',
                fixed: 'left'
            },
            {
                width: '250px',
                title: '기본규칙ID',
                dataIndex: 'id',
                key: 'id',
                fixed: 'left'
            },
            {
                width: '300px',
                title: '기본규칙명',
                dataIndex: 'base_rule_nm',
                key: 'base_rule_nm',
                fixed: 'left'
            },
            {
                width: '160px',
                title: '디폴트이관주기',
                dataIndex: 'dlft_move_cycle',
                key: 'dlft_move_cycle',
            },
            {
                width: '170px',
                title: '디폴트삭제주기',
                dataIndex: 'dlft_delete_cycle',
                key: 'dlft_delete_cycle',
                render: text =>
                    <span>{text}</span>
                ,
            },
            {
                width: '385px',
                title: '설명',
                dataIndex: 'desc',
                key: 'desc',
                render: (text, record) => {

                    const limit = 100;
                    const content = record.desc + "";

                    if (content.length <= limit) {
                        return <div>{content}</div>
                    }

                    const toShow = content.substring(0, limit) + "...";
                    return (
                        <div>
                            {toShow}
                        </div>
                    );
                }
            },
            {
                width: '385px',
                title: '메모',
                dataIndex: 'memo',
                key: 'memo',
                render: (text, record) => {

                    const limit = 100;
                    const content = record.memo + "";

                    if (content.length <= limit) {
                        return <div>{content}</div>
                    }

                    const toShow = content.substring(0, limit) + "...";
                    return (
                        <div>
                            {toShow}
                        </div>
                    );
                }
            },
            {
                width: '250px',
                title: '수정자ID',
                dataIndex: 'modr_id',
                key: 'modr_id',
            },
            {
                width: '200px',
                title: '수정일시',
                dataIndex: 'mod_dtm',
                key: 'mod_dtm',
            },
            {
                width: '250px',
                title: '등록자ID',
                dataIndex: 'regr_id',
                key: 'regr_id',
            },
            {
                width: '200px',
                title: '등록일시',
                dataIndex: 'reg_dtm',
                key: 'reg_dtm',
            },
            {
                width: '150px',
                title: '작업',
                key: 'action',
                fixed: 'right',
                render: (text, record) => {
                    console.log(record);
                    return (
                        <span>
                            <Tooltip placement="top" title={"상세조회"}>

                                 <NavLink to={{
                                     pathname: '/base-rule/detail/' + record.id,
                                     // hash: '#submit',
                                     // search: '?id=' + record.componentId
                                 }}><Button size={"small"} icon={'eye'}/></NavLink>

                            </Tooltip>

                            <Divider type="vertical"/>

                             <Tooltip placement="top" title={"수정"}>
                                 <NavLink to={{
                                     pathname: '/base-rule/edit/' + record.id,
                                     // hash: '#submit',
                                     // search: '?id=' + record.componentId
                                 }}> <Button size={"small"} type={"primary"} icon={"edit"}/></NavLink>
                            </Tooltip>

                            <Divider type="vertical"/>

                            {this.state.allBaseRule.length >= 1 ? (
                                <Popconfirm
                                    icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
                                    placement={"topLeft"}
                                    okType={"danger"}
                                    okText={"삭제"}
                                    cancelText={"취소"}
                                    title="정말로 삭제하시겠습니까?" onConfirm={() => this.handleDelete(record.id, record.base_rule_nm)}>
                                    <Button size={"small"} type={"danger"} icon={"delete"}/>
                                </Popconfirm>
                            ) : null}
                        </span>
                    );
                }
            },
        ];

    }


    componentDidMount() {
        axios.get(endpoint.BASE_RULE)
            .then(res => {
                console.log(res);
                // eslint-disable-next-line array-callback-return
                let i = 0;
                let updatedBaseRule = res.data.data.map(baseRule => {
                    i++;
                    return {
                        no: 100000 + i,
                        id: baseRule.baseRuleId,
                        key: baseRule.baseRuleId,
                        dlft_delete_cycle: baseRule.defaultDeleteCycle,
                        dlft_move_cycle: baseRule.defaultMoveCycle,
                        base_rule_nm: baseRule.baseRuleName,
                        desc: baseRule.description,
                        memo: baseRule.memo,
                        mod_dtm: baseRule.modifiedDate,
                        modr_id: baseRule.modifiedUser,
                        reg_dtm: baseRule.createdDate,
                        regr_id: baseRule.createdUser
                    };
                });
                this.setState({allBaseRule: updatedBaseRule});
            })
    }

    handleDelete = (id, name) => {
        console.log(id);

        axios.delete(endpoint.BASE_RULE + '/' + id )
            .then(res => {
                console.log(res);
                message.success('"' + name + '" 성공적으로 삭제되었습니다!');
                const dataSource = [...this.state.allBaseRule];
                this.setState({ allBaseRule: dataSource.filter(item => item.id !== id) });
            })
            .catch(err => {
                console.log(err);
            });
    };

    render() {
        if (this.state.allBaseRule) {
            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>기본규칙</Breadcrumb.Item>
                                <Breadcrumb.Item>
                                    기본규칙 목록조회
                                </Breadcrumb.Item>
                            </Breadcrumb>
                        </Col>
                        <Col span={3}>

                            <NavLink to={{
                                pathname: "/add-base-rule"
                            }}>
                    <span style={{paddingLeft: '10px'}}>
                        <Button
                            block
                            type="primary"
                            icon="plus-circle"
                            onClick={this.enterIconLoading}
                        >
                            기본규칙 추가
                        </Button>
                    </span>
                            </NavLink>

                        </Col>
                    </Row>

                    <Divider/>

                    <Row>
                        <Table columns={this.columns}
                               dataSource={this.state.allBaseRule} scroll={{x: 2000}}/>
                        총 기본규칙 수 : <Tag style={{fontSize: "medium"}}
                                              color={'geekblue'}>{this.state.allBaseRule.length}</Tag>
                    </Row>
                </Aux>
            )
        }
    }


}

export default AllBaseRule;
