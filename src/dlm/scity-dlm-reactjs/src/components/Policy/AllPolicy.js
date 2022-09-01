import React, {Component} from 'react';
import Aux from '../../layouts/Aux';
import {Breadcrumb, Button, Col, Divider, Icon, message, Popconfirm, Row, Table, Tag, Tooltip} from 'antd';

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance'
import {NavLink} from "react-router-dom";

class AllPolicy extends Component {

    constructor() {
        super();
        this.state = {
            allPolicies: [],
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
                title: '정책ID',
                dataIndex: 'id',
                key: 'id',
                fixed: 'left'
            },
            {
                fixed: 'left',
                width: '300px',
                title: '정책명',
                dataIndex: 'policy_name',
                key: 'policy_name',
            },
            {
                width: '250px',
                title: '소스경로명',
                dataIndex: 'src_path_name',
                key: 'src_path_name',
                render: text =>
                    <span>{text}</span>
                ,
            },
            {
                width: '200px',
                title: '소스파일명',
                dataIndex: 'src_file_name',
                key: 'src_file_name',
            },
            {
                width: '250px',
                title: '목적경로명',
                dataIndex: 'dest_path_name',
                key: 'dest_path_name',
            },
            {
                width: '200px',
                title: '목적파일명',
                dataIndex: 'dest_file_name',
                key: 'dest_file_name',
            },
            {
                width: '250px',
                title: '등록자ID',
                dataIndex: 'created_user',
                key: 'created_user',
            },
            {
                width: '200px',
                title: '등록일시',
                dataIndex: 'created_date',
                key: 'created_date',
            },
            {
                width: '250px',
                title: '수정자ID',
                dataIndex: 'modified_user',
                key: 'modified_user',
            },
            {
                width: '200px',
                title: '수정일시',
                dataIndex: 'modified_date',
                key: 'modified_date',
            },
            {
                width: '200px',
                title: '정책설정일시',
                dataIndex: 'setup_date',
                key: 'setup_date',
            },
            {
                width: '100px',
                title: '사용여부',
                dataIndex: 'status',
                key: 'status',
                render: (text) => {
                    let color = text.toUpperCase() === 'Y' ? 'green' : 'volcano';
                    if (text.toUpperCase() === 'Y')
                        text = 'ACTIVE';
                    else
                        text = 'INACTIVE';

                    return (
                        <Tag color={color} key={text}>
                            {text.toUpperCase()}
                        </Tag>
                    );
                }
            },
            {
                width: '400px',
                title: '관련된 규칙',
                key: 'policy_rule_relations',
                dataIndex: 'policy_rule_relations',
                render: policyRelations => (
                    <span>
                {policyRelations.map(policyRelation => {

                    return (
                        <Tag color="geekblue" key={policyRelation.id}>
                            {policyRelation.name}
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
                                     pathname: '/policy/detail/' + record.id,
                                 }}>
                                     <Button size={"small"} icon={'eye'}/>
                                 </NavLink>
                            </Tooltip>

                            <Divider type="vertical"/>

                             <Tooltip placement="top" title={"수정"}>
                                 <NavLink to={{
                                     pathname: '/policy/edit/' + record.id,
                                 }}>
                                     <Button size={"small"} type={"primary"} icon={"edit"}/>
                                 </NavLink>
                            </Tooltip>

                            <Divider type="vertical"/>

                            {this.state.allPolicies.length >= 1 ? (
                                <Popconfirm
                                    icon={<Icon type="question-circle-o" style={{color: 'red'}}/>}
                                    placement={"topLeft"}
                                    okType={"danger"}
                                    okText={"삭제"}
                                    cancelText={"취소"}
                                    title="정말로 삭제하시겠습니까?"
                                    onConfirm={() => this.handleDelete(record.id, record.policy_name)}>
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

        axios.delete(endpoint.POLICY + '/' + id)
            .then(res => {
                console.log(res);
                message.success('"' + name + '" 성공적으로 삭제되었습니다!');
                const dataSource = [...this.state.allPolicies];
                this.setState({allPolicies: dataSource.filter(item => item.id !== id)});
            })
            .catch(err => {
                console.log(err);
            });
    };

    componentDidMount() {
        axios.get(endpoint.POLICY)
            .then(res => {
                console.log(res);
                // eslint-disable-next-line array-callback-return

                let i = 0;
                let updatedPolicies = res.data.data.map(policy => {
                    i++;
                    return {
                        no: 100000 + i,
                        id: policy.policyId,
                        key: policy.policyId,
                        policy_name: policy.policyName,
                        src_path_name: policy.srcPathName,
                        src_file_name: policy.srcFileName,
                        dest_path_name: policy.destPathName,
                        dest_file_name: policy.destFileName,
                        status: policy.status,
                        created_date: policy.createdDate,
                        created_user: policy.createdUser,
                        modified_date: policy.modifiedDate,
                        modified_user: policy.modifiedUser,
                        setup_date: policy.setupDate,

                        policy_rule_relations: policy.policyRuleRelations.map(policyRelation => {
                            // return ruleRelation.baseRuleId;
                            // return ruleRelation.baseRuleName;
                            return {id: policyRelation.ruleId, name: policyRelation.ruleName};
                        })
                    };
                });
                this.setState({allPolicies: updatedPolicies});
            })
    }

    render() {
        if (this.state.allPolicies) {
            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>정책</Breadcrumb.Item>
                                <Breadcrumb.Item>
                                    정책 목록조회
                                </Breadcrumb.Item>
                            </Breadcrumb>
                        </Col>
                        <Col span={3}>

                            <NavLink to={{
                                pathname: "/add-policy"
                            }}>
                            <span style={{paddingLeft: '10px'}}>
                                <Button
                                    block
                                    type="primary"
                                    icon="plus-circle"
                                    onClick={this.enterIconLoading}
                                >
                                    정책 추가
                                </Button>
                            </span>
                            </NavLink>

                        </Col>
                    </Row>

                    <Divider/>

                    <Row>
                        <Table columns={this.columns} scroll={{x: 2500}}
                               dataSource={this.state.allPolicies}/>
                        총 정책 수: <Tag style={{fontSize: "medium"}}
                                             color={'geekblue'}>{this.state.allPolicies.length}</Tag>
                    </Row>
                </Aux>
            )
        }
    }
}

export default AllPolicy;
