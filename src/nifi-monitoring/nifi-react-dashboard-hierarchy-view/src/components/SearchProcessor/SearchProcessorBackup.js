import React, {Component} from 'react';
import DataTable from 'react-data-table-component';
import {TextField} from "@material-ui/core";

import axios from "../../axios-spring-boot-api-instance";
import * as endpoint from '../../Endpoints';
import {Spin} from "antd";
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from "@material-ui/core/FormControl";
import Input from "@material-ui/core/Input";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import OutlinedInput from "@material-ui/core/OutlinedInput";
import ReactDOM from "react-dom";


const styles = theme => ({
    root: {
        display: "flex",
        flexWrap: "wrap"
    },
    formControl: {
        margin: theme.spacing.unit,
        minWidth: 120
    },
    selectEmpty: {
        marginTop: theme.spacing.unit * 2
    },
    formControl1: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
});

const columns = [
    {
        name: 'Name',
        selector: 'name',
        sortable: true,
    },
    {
        name: 'Level',
        selector: 'level',
        sortable: true,
    },
    {
        name: 'URI',
        selector: 'uri',
        sortable: true,
    },
];

class SearchProcessorBackup extends Component {

    constructor(props) {
        super(props);
        this.state = {
            processGroups: [],
            inputSearchLevel: "",
            inputSearchName: "",
            selected: null,
            age: null,
            inputLabelRef: undefined,
        };
    }

    handleChange(value) {
        this.setState({ selected: value });
    }


    setAge = (age) => {
        this.setState({age: age});
    };

    setInputLabelRef = (refParam) => {
        this.setState({inputLabelRef: refParam});
    };

    handleSearchName = (nameParam) => {
        const name = nameParam.target.value;
        this.setState({
            inputSearchName: name
        });

        axios.get(endpoint.SEARCH + "?name=" + name + "&level=" + this.state.inputSearchLevel)
            .then(res => {
                console.log(res);
                const updatedData = res.data.data.map(component => {
                    return {
                        key: component.flowId,
                        flowId: component.flowId,
                        name: component.name,
                        level: component.level,
                        uri: component.uri,
                    };
                });

                this.setState({processGroups: updatedData});
            });
    };

    handleSearchLevel = (levelParam) => {
        const level = levelParam.target.value;
        this.setState({
            inputSearchLevel: level
        });
        axios.get(endpoint.SEARCH + "?name=" + this.state.inputSearchName + "&level=" + level)
            .then(res => {
                console.log(res);
                const updatedData = res.data.data.map(component => {
                    return {
                        key: component.flowId,
                        flowId: component.flowId,
                        name: component.name,
                        level: component.level,
                        uri: component.uri,
                    };
                });

                this.setState({processGroups: updatedData});
            });
    };

    componentDidMount() {
        axios.get(endpoint.SEARCH)
            .then(res => {
                console.log(res);

                const updatedData = res.data.data.map(component => {
                    return {
                        key: component.flowId,
                        flowId: component.flowId,
                        name: component.name,
                        level: component.level,
                        uri: component.uri,
                    };
                });

                this.setState({processGroups: updatedData});
            });
    }

    render() {
        const { classes } = this.props;
        const { selected } = this.state;

        const labelOffsetWidth = this.state.inputLabelRef
            ? ReactDOM.findDOMNode(this.state.inputLabelRef).offsetWidth
            : 0;

        let show = <Spin tip={"Loading..."}/>;

        if (this.state.processGroups)
            return (
                <div>
                    <DataTable
                        title="NiFi Process Groups List"
                        columns={columns}
                        data={this.state.processGroups}
                        defaultSortField="title"
                        selectableRows={false}
                        // selectableRowsNoSelectAll={false}
                        selectableRowsHighlight={true}
                        expandableRows={true}
                        expandOnRowClicked={true}
                        pagination={true}
                        highlightOnHover={true}
                        striped={true}
                        pointerOnHover={true}
                        noTableHead={false}
                        persistTableHead={false}
                        progressPending={false}
                        noHeader={false}
                        subHeader={true}
                        subHeaderComponent={
                            (
                                <div style={{display: 'flex', alignItems: 'center'}}>

                                    <FormControl variant="outlined" className={classes.formControl1}>
                                        <InputLabel
                                            ref={this.setInputLabelRef}
                                            htmlFor="outlined-age-simple"
                                        >
                                            Select Age
                                        </InputLabel>
                                        <Select
                                            value={this.state.age}
                                            onChange={e => {this.setAge(e.target.value)}}
                                            input={
                                                <OutlinedInput
                                                    labelWidth={labelOffsetWidth}
                                                    name="age"
                                                    id="outlined-age-simple"
                                                />
                                            }
                                        >
                                            <MenuItem value="">
                                                <em>None</em>
                                            </MenuItem>
                                            <MenuItem value={10}>Ten</MenuItem>
                                            <MenuItem value={20}>Twenty</MenuItem>
                                            <MenuItem value={30}>Thirty</MenuItem>
                                        </Select>
                                    </FormControl>

                                    <FormControl className={classes.formControl}>
                                        <InputLabel htmlFor="name">Name</InputLabel>
                                        <Select
                                            name="name"
                                            value={selected}
                                            onChange={event => this.handleChange(event.target.value)}
                                            input={<Input id="name" />}
                                        >
                                            <MenuItem value="hai">Hai</MenuItem>
                                            <MenuItem value="olivier">Olivier</MenuItem>
                                            <MenuItem value="kevin">Kevin</MenuItem>
                                        </Select>
                                        {/* {hasError && <FormHelperText>This is required!</FormHelperText>} */}
                                    </FormControl>

                                    <TextField
                                        value={this.state.inputSearchLevel}
                                        onChange={level => this.handleSearchLevel(level)}
                                        id="outlined-basic"
                                        label="Search Level..."
                                        variant="outlined" size="small" style={{marginRight: '15px'}}/>
                                    <TextField
                                        value={this.state.inputSearchName}
                                        onChange={name => this.handleSearchName(name)}
                                        id="outlined-basic" label="Search Name..."
                                        variant="outlined" size="small"/>

                                </div>
                            )
                        }
                        subHeaderAlign={"left"}
                        fixedHeader={false}
                        fixedHeaderScrollHeight="300px"
                    />
                </div>

            );
        else return show;
    }
}
SearchProcessorBackup.propTypes = {
    classes: PropTypes.object.isRequired
};
export default withStyles(styles)(SearchProcessorBackup);
