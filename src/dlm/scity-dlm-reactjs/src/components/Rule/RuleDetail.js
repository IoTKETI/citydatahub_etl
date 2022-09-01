import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance'

import {Breadcrumb, Button, Col, Divider, Form, Input, InputNumber, Row, Spin, Tag} from 'antd';
import Aux from "../../layouts/Aux";
import Moment from "react-moment";

const {TextArea} = Input;

class RuleDetail extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        rule: null,

    };

    componentDidMount(prevProps, prevState, snapshot) {
        console.log(this.props.history.location.pathname);
        console.log(this.props);

        if (this.props.match.params.id) {
            axios({
                method: 'get',
                url: endpoint.RULE + '/' + this.props.match.params.id,
                cors: true,
                withCredentials: true
            })
                .then(res => {
                    console.log(res.data.data);
                    this.setState({rule: res.data.data});
                });
        }
    }

    render() {
        const formItemLayout = {
            labelCol: {
                xs: {span: 6},
                sm: {span: 6},
            },
            wrapperCol: {
                xs: {span: 16},
                sm: {span: 16},
            },
        };

        const rule = this.state.rule;

        if (rule) {
            let baseRuleRelation = null;

            if (rule.ruleBaseRuleRelations) {
                baseRuleRelation = rule.ruleBaseRuleRelations.map(relation => {
                    return <Tag color={'green'} key={relation.baseRuleId}>{relation.baseRuleName}</Tag>;
                });
            }
            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>규칙</Breadcrumb.Item>
                                <Breadcrumb.Item>규칙 상세조회</Breadcrumb.Item>
                                <Breadcrumb.Item>{rule.ruleId}</Breadcrumb.Item>

                                <Breadcrumb.Item>

                                    <NavLink to={{
                                        pathname: "/rule/edit/" + rule.ruleId
                                    }}>
                                        <span style={{paddingLeft: '10px'}}>
                                            {/*Edit current Base Rule*/}
                                            <Button type={"danger"} size={"small"} icon={'edit'}>수정</Button>
                                        </span>
                                    </NavLink>
                                </Breadcrumb.Item>

                            </Breadcrumb>
                        </Col>
                        <Col span={3}>

                            <NavLink to={{
                                pathname: "/all-rule"
                            }}>
                            <span style={{paddingLeft: '10px'}}>
                                {/*Go to all rule page*/}
                                <Button
                                    block
                                    icon="unordered-list"
                                    type="primary"
                                >
                                    규칙 목록조회
                                </Button>
                            </span>
                            </NavLink>

                        </Col>
                    </Row>

                    <Divider/>

                    <Row>
                        <Form {...formItemLayout}>
                            <Col span={12}>
                                <Form.Item label="규칙ID">
                                    <Input style={{width: '30%'}} placeholder={"Rule ID"} defaultValue={rule.ruleId}
                                           readOnly/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="규칙명">
                                    <Input placeholder={"Rule Name"} defaultValue={rule.ruleName} readOnly/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="이관주기">
                                    <InputNumber style={{width: '30%'}} min={0} readOnly defaultValue={rule.moveCycle}/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="삭제주기">
                                    <InputNumber style={{width: '30%'}} min={0} readOnly
                                                 defaultValue={rule.deleteCycle}/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="등록자ID">
                                    <Input min={0} readOnly defaultValue={rule.createdUser}/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="수정자ID">
                                    <Input min={0} readOnly defaultValue={rule.modifiedUser}/>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="등록일시">
                                    <Tag style={{fontSize: "small"}}
                                         color="blue"><Moment>{rule.createdDate}</Moment></Tag>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="수정일시">
                                    <Tag style={{fontSize: "small"}}
                                         color="blue"><Moment>{rule.modifiedDate}</Moment></Tag>
                                </Form.Item>
                            </Col>
                            <Row>
                                {baseRuleRelation ?

                                    <Col span={12}>
                                        <Form.Item label="관련된 기본규칙">
                                            {baseRuleRelation}
                                        </Form.Item>
                                    </Col> : ''}
                            </Row>

                            <Col span={12}>
                                <Form.Item label="설명">
                                    <TextArea
                                        defaultValue={rule.description}
                                        readOnly
                                        placeholder="description"
                                        autoSize
                                    />
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item label="메모">
                                    <TextArea
                                        defaultValue={rule.memo}
                                        readOnly
                                        placeholder="Memo"
                                        autoSize
                                    />
                                </Form.Item>
                            </Col>
                        </Form>
                    </Row>
                </Aux>
            );
        }
        return (
            <div style={{textAlign: 'center'}}>
                <Spin/>
            </div>
        );

    }
}

export default RuleDetail;
