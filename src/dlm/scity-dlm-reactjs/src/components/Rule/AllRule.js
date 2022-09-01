import React, {Component} from 'react';
import Aux from '../../layouts/Aux';
import {Breadcrumb, Button, Col, Divider, Icon, message, Popconfirm, Row, Table, Tag, Tooltip} from 'antd';

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance'
import {NavLink} from "react-router-dom";

class AllRule extends Component {

    constructor() {
        super();
        this.state = {
            allRule: [],
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
                width: '200px',
                title: '규칙ID',
                dataIndex: 'id',
                key: 'id',
                fixed: 'left'
            },
            {
                fixed: 'left',
                width: '300px',
                title: '규칙명',
                dataIndex: 'rule_nm',
                key: 'rule_nm',
            },
            {
                width: '160px',
                title: '이관주기',
                dataIndex: 'move_cycle',
                key: 'move_cycle',
            },
            {
                width: '170px',
                title: '삭제주기',
                dataIndex: 'delete_cycle',
                key: 'delete_cycle',
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
                width: '400px',
                title: '관련된 기본규칙',
                key: 'base_rule_relations',
                dataIndex: 'base_rule_relations',
                render: tags => (
                    <span>
                {tags.map(tag => {

                    return (
                        <Tag color="green" key={tag.id}>
                            {tag.name}
                        </Tag>

                    );
                })}
            </span>
                ),
            },
            {
                width: '150px',
                title: '작업',
                key: 'action',
                fixed: 'right',
                render: (text, record) => {
                    return (
                        <span>
                            <Tooltip placement="top" title={"상세조회"}>

                                 <NavLink to={{
                                     pathname: '/rule/detail/' + record.id,
                                     // hash: '#submit',
                                     // search: '?id=' + record.componentId
                                 }}><Button size={"small"} icon={'eye'}/></NavLink>

                            </Tooltip>

                            <Divider type="vertical"/>

                             <Tooltip placement="top" title={"수정"}>
                                 <NavLink to={{
                                     pathname: '/rule/edit/' + record.id,
                                     // hash: '#submit',
                                     // search: '?id=' + record.componentId
                                 }}> <Button size={"small"} type={"primary"} icon={"edit"}/></NavLink>
                            </Tooltip>

                            <Divider type="vertical"/>

                            {this.state.allRule.length >= 1 ? (
                                <Popconfirm
                                    icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
                                    placement={"topLeft"}
                                    okType={"danger"}
                                    okText={"삭제"}
                                    cancelText={"취소"}
                                    title="정말로 삭제하시겠습니까?" onConfirm={() => this.handleDelete(record.id, record.rule_nm)}>
                                    <Button size={"small"} type={"danger"} icon={"delete"}/>
                                </Popconfirm>
                            ) : null}

                        </span>
                    );
                }
            },
        ];
    }

    handleDelete = (id, name) => {
        console.log(id);

        axios.delete(endpoint.RULE + '/' + id )
            .then(res => {
                console.log(res);
                message.success('"' + name + '" 성공적으로 삭제되었습니다!');
                const dataSource = [...this.state.allRule];
                this.setState({ allRule: dataSource.filter(item => item.id !== id) });
            })
            .catch(err => {
                console.log(err);
            });
    };

    componentDidMount() {
        axios.get(endpoint.RULE)
            .then(res => {
                console.log(res);
                // eslint-disable-next-line array-callback-return
                let i = 0;
                let updatedRule = res.data.data.map(rule => {
                    i++;
                    return {
                        no: 100000 + i,
                        id: rule.ruleId,
                        key: rule.ruleId,
                        delete_cycle: rule.deleteCycle,
                        move_cycle: rule.moveCycle,
                        rule_nm: rule.ruleName,
                        desc: rule.description,
                        memo: rule.memo,
                        mod_dtm: rule.modifiedDate,
                        modr_id: rule.modifiedUser,
                        reg_dtm: rule.createdDate,
                        regr_id: rule.createdUser,
                        base_rule_relations: rule.ruleBaseRuleRelations.map(ruleRelation =>{
                            // return ruleRelation.baseRuleId;
                            // return ruleRelation.baseRuleName;
                            return {id: ruleRelation.baseRuleId, name: ruleRelation.baseRuleName};
                        })
                    };
                });
                this.setState({allRule: updatedRule});
            })
    }

    render() {
        if (this.state.allRule) {
            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>규칙</Breadcrumb.Item>
                                <Breadcrumb.Item>
                                    규칙 목록조회
                                </Breadcrumb.Item>
                            </Breadcrumb>
                        </Col>
                        <Col span={3}>

                            <NavLink to={{
                                pathname: "/add-rule"
                            }}>
                            <span style={{paddingLeft: '10px'}}>
                                <Button
                                    block
                                    type="primary"
                                    icon="plus-circle"
                                    onClick={this.enterIconLoading}
                                >
                                    규칙 추가
                                </Button>
                            </span>
                            </NavLink>

                        </Col>
                    </Row>

                    <Divider/>

                    <Row>
                        <Table columns={this.columns} scroll={{ x: 2400 }} dataSource={this.state.allRule}/>
                        총 규칙 : <Tag style={{fontSize: "medium"}} color={'geekblue'}>{this.state.allRule.length}</Tag>
                    </Row>
                </Aux>
            )
        }
    }
}

export default AllRule;
