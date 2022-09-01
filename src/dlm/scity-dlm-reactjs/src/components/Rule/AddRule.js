import React, {Component} from 'react';
import {Redirect} from 'react-router-dom';

import {Breadcrumb, Button, Col, Divider, Form, Input, InputNumber, Row, message, Select} from 'antd';
import {NavLink} from "react-router-dom";
import Aux from "../../layouts/Aux";

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance';
import * as lang from '../../utilities/lang-kr';

const {TextArea} = Input;

class AddRule extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        redirect: false,
        allBaseRuleNames: [],

        // Selected base rules to be saved.
        selectedItems: [],
    };

    componentDidMount() {
        axios.get(endpoint.BASE_RULE)
            .then(res => {
                const updatedBaseRule = res.data.data.map(baseRule => {
                    return {
                        key: baseRule.baseRuleId,
                        value: baseRule.baseRuleName
                    };
                });

                this.setState({allBaseRuleNames: updatedBaseRule});
            });
    }

    handleChange = selectedItems => {
        this.setState({ selectedItems });
    };

    handleSubmit = e => {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {

            /*Start checking duplicate name*/

            /*End checking duplicate name*/


            if (!err) {
                let hide = message.loading('저장중...');
                // console.log('Received values of form: ', values);
                const rule = JSON.stringify(values);
                console.log("Rule to save: ", rule);

                axios.post(endpoint.RULE, values)
                    .then(res => {
                        console.log(res);
                        message.success('규칙이 성공적으로 저장되었습니다!');
                        this.setState({ redirect: true })
                    })
                    .catch(err => {
                        setTimeout(hide, 0);
                        console.log(err);
                        message.error('규칙 저장 실패!');
                    })
                ;
            }
        });
    };
    compareToFirstPassword = (rule, value, callback) => {
        const {form} = this.props;
        console.log(value);
        console.log(form.getFieldValue('moveCycle'));
        if (value && value <= form.getFieldValue('moveCycle')) {
            callback('삭제주기는 이관주기보다 짧을 수 없습니다!');
        } else {
            callback();
        }
    };
    validateToNextPassword = (rule, value, callback) => {
        // console.log(rule);
        // console.log(value);
        const {form} = this.props;
        if (value && this.state.confirmDirty) {
            form.validateFields(['deleteCycle'], {force: true});
        }
        callback();
    };

    render() {


        if (!this.state.allBaseRuleNames){
            return "하나 이상의 기본규칙이 있어야 합니다!"
        }

        const { selectedItems } = this.state;

        const filteredOptions = this.state.allBaseRuleNames.filter(o => {
            return !selectedItems.includes(o.value)
        });

        console.log(selectedItems);

        if (this.state.redirect) {
            return <Redirect to='/all-rule'/>;
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

        return (
            <Aux>
                <Row>
                    <Col span={21}>
                        <Breadcrumb>
                            <Breadcrumb.Item>규칙</Breadcrumb.Item>
                            <Breadcrumb.Item>
                                규칙 추가
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
                    <span style={{color: "#f5222d"}}>*&nbsp;</span><span>{lang.requireField}</span>
                    <Form {...formItemLayout} onSubmit={this.handleSubmit}>
                        <Form.Item label="규칙명">
                            {getFieldDecorator('ruleName', {
                                rules: [
                                    {
                                        required: true,
                                        message: '규칙명을 입력해주세요!',
                                    },
                                ],
                            })(<Input placeholder={"Rule Name"}/>)}
                        </Form.Item>
                        <Form.Item label="이관주기" hasFeedback>
                            {getFieldDecorator('moveCycle', {
                                rules: [
                                    {
                                        required: true,
                                        message: '이관주기를 설정해주세요!',
                                    },
                                    {
                                        validator: this.validateToNextPassword,
                                    },

                                ],
                            })(<InputNumber min={0}/>)}
                        </Form.Item>
                        <Form.Item label="삭제주기" hasFeedback>
                            {getFieldDecorator('deleteCycle', {
                                rules: [
                                    {
                                        required: true,
                                        message: '삭제주기를 설정해주세요!',
                                    },
                                    {
                                        validator: this.compareToFirstPassword,
                                    },
                                ],
                            })(<InputNumber min={0} />)}
                        </Form.Item>

                        <Form.Item label="관련된 기본규칙" hasFeedback>
                            {getFieldDecorator('baseRuleList', {
                                rules: [{ required: true, message: '기본규칙을 선택해주세요!' }],
                            })(
                                <Select
                                    mode="multiple"
                                    placeholder="Please select Base Rule"
                                    // value={selectedItems}
                                    onChange={this.handleChange}
                                    style={{ width: '100%' }}>

                                    {filteredOptions.map(item => (
                                        /*Use below commented code instead of above to select base rule without removing a selected base rule*/
                                    // {this.state.allBaseRuleNames.map(item => (
                                        <Select.Option key={item.key} value={item.key}>
                                            {item.value}
                                        </Select.Option>
                                    ))}
                                </Select>
                            )}
                        </Form.Item>

                        <Form.Item label="설명">
                            {getFieldDecorator('description', {
                                rules: [{required: false}],
                            })(
                                <TextArea
                                    placeholder="description"
                                    autoSize={{minRows: 5, maxRows: 20}}
                                />
                            )}
                        </Form.Item>
                        <Form.Item label="메모">
                            {getFieldDecorator('memo', {
                                rules: [{required: false}],
                            })(
                                <TextArea
                                    placeholder="Memo"
                                    autoSize={{minRows: 5, maxRows: 20}}
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
}

const WrappedAddRuleForm = Form.create({name: 'register'})(AddRule);

export default WrappedAddRuleForm;
