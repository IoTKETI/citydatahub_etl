import React, {Component} from 'react';
import {NavLink, Redirect} from 'react-router-dom';

import {Breadcrumb, Button, Col, Divider, Form, Input, InputNumber, message, Row, Spin} from 'antd';
import Aux from "../../../layouts/Aux";

import * as endpoint from '../../../utilities/Endpoints';
import axios from '../../../utilities/axios-spring-boot-api-instance';
import * as lang from '../../../utilities/lang-kr';

const {TextArea} = Input;

class EditBaseRule extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        redirect: false,
        baseRule: null,

    };

    handleSubmit = e => {

        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {

            /*Start checking duplicate base name*/

            /*End checking duplicate base name*/


            if (!err) {
                let hide = message.loading('저장중...');
                // console.log('Received values of form: ', values);
                const baseRule = JSON.stringify(values);
                console.log("Base Rule to edit: ", baseRule);
                axios.put(endpoint.BASE_RULE + '/' + this.state.baseRule.baseRuleId, values)
                    .then(res => {
                        setTimeout(hide, 0);
                        console.log(res);
                        message.success("\"" + values.baseRuleName + '" 기본규칙이 성공적으로 수정되었습니다!', 3);
                        this.setState({redirect: true});

                    })
                    .catch(err => {
                        console.log(err);
                        setTimeout(hide, 0);
                        message.error('수정실패!');
                    })
                ;
            }
        });
    };

    /* handleConfirmBlur = e => {
         const {value} = e.target;
         this.setState({confirmDirty: this.state.confirmDirty || !!value});
     };*/

    compareToFirstPassword = (rule, value, callback) => {
        const {form} = this.props;
        console.log(value);
        console.log(form.getFieldValue('defaultMoveCycle'));
        if (value && value <= form.getFieldValue('defaultMoveCycle')) {
            callback('디폴트삭제주기는 디폴트이관주기보다 짧을 수 없습니다!');
        } else {
            callback();
        }
    };

    validateToNextPassword = (rule, value, callback) => {
        // console.log(rule);
        // console.log(value);
        const {form} = this.props;
        if (value && this.state.confirmDirty) {
            form.validateFields(['defaultDeleteCycle'], {force: true});
        }
        callback();
    };

    componentDidMount(prevProps, prevState, snapshot) {
        console.log(this.props.history.location.pathname);
        console.log(this.props);

        if (this.props.match.params.id) {
            axios({
                method: 'get',
                url: endpoint.BASE_RULE + '/' + this.props.match.params.id,
                cors: true,
                withCredentials: true
            })
                .then(res => {
                    console.log(res);

                    this.setState({baseRule: res.data.data});
                });
        }
    }


    render() {

        if (this.state.redirect) {
            return <Redirect to='/all-base-rule'/>;
        }

        const {getFieldDecorator} = this.props.form;

        const formItemLayout = {
            labelCol: {
                xs: {span: 6},
                sm: {span: 6},
            },
            wrapperCol: {
                xs: {span: 12},
                sm: {span: 12},
            },
        };
        const tailFormItemLayout = {
            labelCol: {span: 6},
            wrapperCol: {span: 12, offset: 6},
        };

        const baseRule = this.state.baseRule;
        if (baseRule) {
            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>기본규칙</Breadcrumb.Item>
                                <Breadcrumb.Item>
                                    기본규칙 수정
                                </Breadcrumb.Item>
                            </Breadcrumb>
                        </Col>
                        <Col span={3}>

                            <NavLink to={{
                                pathname: "/all-base-rule"
                            }}>
                            <span style={{paddingLeft: '10px'}}>
                                {/*Go to all base rule page*/}
                                <Button
                                    block
                                    icon="unordered-list"
                                    type="primary"
                                >
                                    기본규칙 목록조회
                                </Button>
                            </span>
                            </NavLink>
                        </Col>
                    </Row>

                    <Divider/>

                    <Row>
                        <span style={{color: "#f5222d"}}>*&nbsp;</span><span>{lang.requireField}</span>
                        <Form {...formItemLayout} onSubmit={this.handleSubmit}>
                            <Form.Item label="기본규칙명">
                                {getFieldDecorator('baseRuleName', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '기본규칙명을 입력해주세요!',
                                        },
                                    ],
                                    initialValue: baseRule.baseRuleName
                                })(<Input placeholder={"Base Rule Name"}
                                          allowClear/>)}
                            </Form.Item>

                            <Form.Item label="디폴트이관주기" hasFeedback>
                                {getFieldDecorator('defaultMoveCycle', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '이관주기를 설정해주세요!',
                                        },
                                        {
                                            validator: this.validateToNextPassword,
                                        },
                                    ],
                                    initialValue: baseRule.defaultMoveCycle
                                })(<InputNumber min={1}/>)}
                            </Form.Item>


                            <Form.Item label="디폴트삭제주기" hasFeedback>
                                {getFieldDecorator('defaultDeleteCycle', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '디폴트삭제주기를 입력해주세요!',
                                        },
                                        {
                                            validator: this.compareToFirstPassword,
                                        },
                                    ],
                                    initialValue: baseRule.defaultDeleteCycle
                                })(<InputNumber min={1}/>)}
                            </Form.Item>


                            <Form.Item label="설명">
                                {getFieldDecorator('description', {
                                    rules: [{required: false}],
                                    initialValue: baseRule.description
                                })(
                                    <TextArea
                                        placeholder="description"
                                        autoSize={{minRows: 5, maxRows: 20}}
                                        allowClear
                                    />
                                )}
                            </Form.Item>

                            <Form.Item label="메모">
                                {getFieldDecorator('memo', {
                                    rules: [{required: false}],
                                    initialValue: baseRule.memo
                                })(
                                    <TextArea
                                        placeholder="Memo"
                                        autoSize={{minRows: 5, maxRows: 20}}
                                        allowClear
                                    />
                                )}
                            </Form.Item>

                            <Form.Item {...tailFormItemLayout}>
                                <Row>
                                    <Col span={8}>
                                        <Button type="primary" block htmlType="submit" icon={"edit"}>
                                            저장
                                        </Button>
                                    </Col>
                                </Row>
                            </Form.Item>
                        </Form>

                    </Row>
                </Aux>
            );
        }
        return (
            <div style={{textAlign: 'center'}}>
                <Spin size={"large"}/>
            </div>
        );

    }
}

const WrappedEditBaseRuleForm = Form.create({name: 'register'})(EditBaseRule);

export default WrappedEditBaseRuleForm;
