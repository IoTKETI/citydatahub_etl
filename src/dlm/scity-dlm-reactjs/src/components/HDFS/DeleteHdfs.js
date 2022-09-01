import React, {Component} from 'react';

import {Breadcrumb, Button, Col, Divider, Form, Input, message, Row} from 'antd';
import Aux from "../../layouts/Aux";

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance'

class DeleteHdfs extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
    };

    handleSubmit = e => {

        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {

            if (!err) {
                const hide = message.loading('삭제중...');
                console.log("HDFS to delete: ", values);
                const fileName = values.fileName;
                const pathName = values.pathName;

                axios.delete(endpoint.HDFS + "/test?name=" + fileName + "&path=" + pathName)
                    .then(res => {
                        setTimeout(hide, 0);
                        console.log(res);
                        message.success('성공적으로 파일이 이동되었습니다!', 3);
                    })
                    .catch(err => {
                        console.log(err);
                        setTimeout(hide, 0);
                        message.error('파일 이동 실패!');
                    })
                ;
            }
        });
    };

    render() {

        const {getFieldDecorator} = this.props.form;

        const formItemLayout = {
            labelCol: {
                xs: {span: 8},
                sm: {span: 8},
            },
            wrapperCol: {
                xs: {span: 8},
                sm: {span: 8},
            },
        };
        const tailFormItemLayout = {
            labelCol: {span: 8},
            wrapperCol: {span: 8, offset: 8},
        };

        return (
            <Aux>
                <Row>
                    <Col span={21}>
                        <Breadcrumb>
                            <Breadcrumb.Item>하둡 파일 삭제</Breadcrumb.Item>
                        </Breadcrumb>
                    </Col>
                </Row>
                <Divider/>
                <Row>
                    <Form {...formItemLayout} onSubmit={this.handleSubmit}>
                        <Form.Item label="파일명">
                            {getFieldDecorator('fileName', {
                                rules: [
                                    {
                                        required: true,
                                        message: '파일명을 입력해주세요!'
                                    },
                                ],
                            })(<Input/>)}
                        </Form.Item>
                        <Form.Item label="목적경로명">
                            {getFieldDecorator('pathName', {
                                rules: [
                                    {
                                        required: true,
                                        message: '목적경로명을 입력해주세요!',
                                    },
                                ],
                            })(<Input/>)}
                        </Form.Item>

                        <Form.Item {...tailFormItemLayout}>
                            <Row>
                                <Col span={8}>
                                    <Button type="danger" block htmlType="submit" icon={"edit"}>
                                       삭제
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

const WrappedDeleteHdfsForm = Form.create({name: 'register'})(DeleteHdfs);

export default WrappedDeleteHdfsForm;
